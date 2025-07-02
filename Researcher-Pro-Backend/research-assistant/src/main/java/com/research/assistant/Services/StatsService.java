package com.research.assistant.Services;


import com.research.assistant.Model.ResearchAction;
import com.research.assistant.Repositories.ResearchActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class StatsService {

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
}
