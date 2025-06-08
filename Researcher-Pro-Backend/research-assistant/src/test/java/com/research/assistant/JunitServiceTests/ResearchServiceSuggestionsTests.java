package com.research.assistant.JunitServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.ResearchRequest;
import com.research.assistant.ResearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ResearchServiceSuggestionsTests {


    private ResearchRequest researchRequest;
    private ResearchService researchService;
    private ObjectMapper dummyMapper;


    @BeforeEach
    void init(){
        researchRequest = new ResearchRequest();
        researchRequest.setContent("This is some content for extra suggestions");
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient mockClient = Mockito.mock(WebClient.class);
        dummyMapper = new ObjectMapper();
        Mockito.when(mockBuilder.build()).thenReturn(mockClient);
        researchService = new ResearchService(mockBuilder, dummyMapper);
    }


    @Test
    public void buildPromptForSuggestions(){
        researchRequest.setOperation("suggest");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "Based on the following content: suggest related" +
                "topics and further reading. Format the response with " +
                "clear heading and billet points:\n\n";
        expectedPrompt += "This is some content for extra suggestions";
        assertEquals(expectedPrompt, actualPrompt);
    }

    @Test
    public void buildPromptThrowIllegalExceptionCase(){
        researchRequest.setOperation("Illegal Suggestion");
        assertThrows(IllegalArgumentException.class, () -> researchService.buildPrompt(researchRequest));

    }

    @Test
    public void extractTestFromResponseSuggestions(){
        String response = "{\n" +
                "    \"candidates\": [\n" +
                "        {\n" +
                "            \"content\": {\n" +
                "                \"parts\": [\n" +
                "                    {\n" +
                "                        \"text\": \"1.  *Harry Potter and the Sorcerer's Stone*\\n2.  *Harry Potter and the Prisoner of Azkaban*\\n\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"role\": \"model\"\n" +
                "            },\n" +
                "            \"finishReason\": \"TEST\",\n" +
                "            \"avgLogprobs\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"usageMetadata\": {\n" +
                "        \"promptTokenCount\": 0,\n" +
                "        \"candidatesTokenCount\": 0,\n" +
                "        \"totalTokenCount\": 0,\n" +
                "        \"promptTokensDetails\": [\n" +
                "            {\n" +
                "                \"modality\": \"TEST\",\n" +
                "                \"tokenCount\": 0\n" +
                "            }\n" +
                "        ],\n" +
                "        \"candidatesTokensDetails\": [\n" +
                "            {\n" +
                "                \"modality\": \"TEST\",\n" +
                "                \"tokenCount\": 0\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"modelVersion\": \"gemini-2.0-flash\",\n" +
                "    \"responseId\": \"0\"\n" +
                "}";
        String actualResponse = researchService.extractTextFromResponse(response);
        String expectedResponse = "1.  *Harry Potter and the Sorcerer's Stone*\n2.  *Harry Potter and the Prisoner of Azkaban*\n";
        assertEquals(expectedResponse, actualResponse);
    }
}

