package com.research.assistant.Services;


import com.research.assistant.Model.ResearchAction;
import com.research.assistant.Repositories.ResearchActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class StatsService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private ResearchActionRepository researchActionRepository;

    public String getStats(){
        String result = "";
        Optional<ResearchAction> summarize = researchActionRepository.findFirstByAction("summarize");
        Optional<ResearchAction> suggest = researchActionRepository.findFirstByAction("suggest");
        Optional<ResearchAction> citation = researchActionRepository.findFirstByAction("Citation");

        if(summarize.isPresent() && suggest.isPresent() && citation.isPresent()) {
            int summaryTotalCount = summarize.get().getTotalCount();
            String summaryLastAccessed = summarize.get().getLastTimeAccessed();
            int suggestTotalCount = suggest.get().getTotalCount();
            String suggestLastAccessed = suggest.get().getLastTimeAccessed();
            int citationTotalCount = citation.get().getTotalCount();
            String citationLastAccessed = citation.get().getLastTimeAccessed();

            result += "Summarization Usage: Amount: " + summaryTotalCount + ", Last Time Used: " + summaryLastAccessed +"\n\n";
            result += "Suggestions Usage: Amount: " + suggestTotalCount + ", Last Time Used: " + suggestLastAccessed +"\n\n";
            result += "Citation Usage: Amount: " + citationTotalCount + ", Last Time Used: " + citationLastAccessed +"\n\n";

            return result;
        }
        else{
            return "Error Please Try Again";
        }
    }

    public void deleteStats() {
        Optional<ResearchAction> summarize = researchActionRepository.findFirstByAction("summarize");
        Optional<ResearchAction> suggest = researchActionRepository.findFirstByAction("suggest");
        Optional<ResearchAction> citation = researchActionRepository.findFirstByAction("Citation");

        if(summarize.isPresent() && suggest.isPresent() && citation.isPresent()) {
            summarize.get().setTotalCount(0);
            summarize.get().setLastTimeAccessed("00/00/0000");
            summarize.get().setResearchRequestList(new ArrayList<>());
            suggest.get().setTotalCount(0);
            suggest.get().setLastTimeAccessed("00/00/0000");
            suggest.get().setResearchRequestList(new ArrayList<>());
            citation.get().setTotalCount(0);
            citation.get().setLastTimeAccessed("00/00/0000");
            citation.get().setResearchRequestList(new ArrayList<>());
            researchActionRepository.save(summarize.get());
            researchActionRepository.save(suggest.get());
            researchActionRepository.save(citation.get());

        }
        else{
            // DO NOTHING FOR NOW
            // THIS ELSE BLOCK SHOULD NEVER BE REACHED
        }
    }

    public String getGeminiRecommendations() {
        String prompt = buildPrompt();




        return "";
    }

    private String buildPrompt() {
        String result = "";
        result += "Hello Gemini, I need you to give some reading recommendations to the user. Below I have provided " +
                "you with some of their recent usages and reading. The elements include some readings they used for summaries, or " +
                "even some links that they have cited. Try you best to find a ball park, and give a few article names and maybe their links " +
                "for some suggested readings. Also if there is nothing provided below, tell user that not enough stats for recommendations\n\n";

        Optional<ResearchAction> summarize = researchActionRepository.findFirstByAction("summarize");
        Optional<ResearchAction> suggest = researchActionRepository.findFirstByAction("suggest");
        Optional<ResearchAction> citation = researchActionRepository.findFirstByAction("Citation");

        if(summarize.isPresent() && suggest.isPresent() && citation.isPresent()) {
            int summarizeListSize = summarize.get().getResearchRequestList().size();
            int suggestListSize = suggest.get().getResearchRequestList().size();
            int citationListSize = citation.get().getResearchRequestList().size();

            if(summarizeListSize > 0){
                result += "1. " + summarize.get().getResearchRequestList().getLast().getContent() + "\n\n";
            }
            if(suggestListSize > 0){
                result += "2. " + suggest.get().getResearchRequestList().getLast().getContent() + "\n\n";
            }

            if(citationListSize >= 3) {
                int j = 3;
                for (int i = citationListSize - 1; i >= citationListSize - 3; i--) {
                    result += j + ". " + citation.get().getResearchRequestList().get(i).getContent() + "\n\n";
                }
            }

        }
        else {
            // DO NOTHING
            // SHOULD NEVER REACH THIS BLOCK
        }

        return result;
    }
}
