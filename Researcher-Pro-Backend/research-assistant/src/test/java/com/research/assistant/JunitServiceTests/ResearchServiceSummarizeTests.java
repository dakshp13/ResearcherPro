package com.research.assistant.JunitServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.ResearchRequest;
import com.research.assistant.ResearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResearchServiceSummarizeTests {
    private ResearchRequest researchRequest;
    private ResearchService researchService;


    @BeforeEach
    void init(){
        researchRequest = new ResearchRequest();
        researchRequest.setContent("This is some article to summarize");
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient mockClient = Mockito.mock(WebClient.class);
        ObjectMapper dummyMapper = new ObjectMapper();
        Mockito.when(mockBuilder.build()).thenReturn(mockClient);
        researchService = new ResearchService(mockBuilder, dummyMapper);
    }


    @Test
    public void buildPromptDetailedSummary(){
        researchRequest.setOperation("summarize:detailed");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "Provide a clear and full summary of the " +
                "following text in a lot of detail. If there are any fundamental concepts then explain them well. Ensure one or two paragraphs" +
                "of well written summary. Also make sure its clear for the reader to follow along:\n\n";
        expectedPrompt += "This is some article to summarize";
        assertEquals(expectedPrompt, actualPrompt);
    }


    @Test
    public void buildPromptBriefSummary(){
        researchRequest.setOperation("summarize:brief");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "Provide a brief and concise summary of the " +
                "following text. If there are any fundamental concepts then explain them in short. Ensure only a few sentences" +
                "of well written summary. Also make sure its clear for the reader to follow along:\n\n";
        expectedPrompt += "This is some article to summarize";
        assertEquals(expectedPrompt, actualPrompt);
    }
}

