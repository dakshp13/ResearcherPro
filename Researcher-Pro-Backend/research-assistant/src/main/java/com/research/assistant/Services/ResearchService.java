package com.research.assistant.Services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.GeminiResponse;
import com.research.assistant.Model.ResearchAction;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Repositories.ResearchActionRepository;
import com.research.assistant.Repositories.ResearchRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class ResearchService {
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private ResearchActionRepository researchActionRepository;

    @Autowired
    private ResearchRequestRepository researchRequestRepository;


    String action;

    @Autowired
    Optional<ResearchAction> researchAction;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ResearchService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper){
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    public String getAction() {
        return action;
    }

    public String processContent(ResearchRequest request){
        String prompt = buildPrompt(request);
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[]{
                               Map.of("text", prompt)
                        })
                }
        );
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        researchAction = researchActionRepository.findFirstByAction(action);

        updateMongoDbResearchRequest(request);


        if(researchAction.isPresent()){
            updateMongoDbResearchAction(researchAction.get(), request);
        }
        else{
            return "Error in API Call: Broken Please Fix!";
        }

        return extractTextFromResponse(response);

    }

    public String buildPrompt(ResearchRequest request){
        StringBuilder prompt = new StringBuilder();
        if(request.getOperation().equals("summarize:detailed")) {
           action = "summarize";
            prompt.append("Provide a clear and full summary of the " +
                    "following text in a lot of detail. If there are any fundamental concepts then explain them well. Ensure one or two paragraphs" +
                    "of well written summary. Also make sure its clear for the reader to follow along:\n\n");
        } else if (request.getOperation().equals("summarize:brief")) {
            action = "summarize";
            prompt.append("Provide a brief and concise summary of the " +
                    "following text. If there are any fundamental concepts then explain them in short. Ensure only a few sentences" +
                    "of well written summary. Also make sure its clear for the reader to follow along:\n\n");
        } else if (request.getOperation().equals("suggest")) {
            action = "suggest";
            prompt.append("Based on the following content: suggest related" +
                    "topics and further reading. Format the response with " +
                    "clear heading and bullet points:\n\n");
        } else if (request.getOperation().equals("IEEE Citation") || request.getOperation().equals("APA Citation")
            || request.getOperation().equals("MLA Citation") || request.getOperation().equals("Chicago-Style Citation")) {
            action = "Citation";
            prompt.append("If " + request.getContent() + " looks like a valid citable link then cite it using this" +
                    "format: " + request.getOperation() + ", otherwise just state that its not a citable link. Once again here is the link:\n\n");
        }
        else {
            throw new IllegalArgumentException("Unknown Operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }

    public String extractTextFromResponse(String response) {
        try{
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);
            if(geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()){
                GeminiResponse.Candidate firstCandidate = geminiResponse.getCandidates().get(0);
                if(firstCandidate.getContent() != null &&
                        firstCandidate.getContent().getParts() != null &&
                        !firstCandidate.getContent().getParts().isEmpty()){
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }
            return "No content found in response";
        } catch (Exception e) {
            return "Error Parsing: " + e.getMessage();
        }

    }

    private void updateMongoDbResearchAction(ResearchAction inputResearchAction, ResearchRequest inputResearchRequest){
       inputResearchAction.setTotalCount(inputResearchAction.getTotalCount()+1);
       LocalDate today = LocalDate.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
       String formattedDate = today.format(formatter);
       inputResearchAction.setLastTimeAccessed(formattedDate);
       inputResearchAction.addToResearchRequestList(inputResearchRequest);
       researchActionRepository.save(inputResearchAction);
    }

    private void updateMongoDbResearchRequest(ResearchRequest inputResearchRequest){
        researchRequestRepository.insert(inputResearchRequest);
        researchRequestRepository.save(inputResearchRequest);
    }


}
