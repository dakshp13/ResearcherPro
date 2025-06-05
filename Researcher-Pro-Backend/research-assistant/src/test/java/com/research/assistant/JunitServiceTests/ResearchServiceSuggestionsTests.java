package com.research.assistant.JunitServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.ResearchRequest;
import com.research.assistant.ResearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ResearchServiceSuggestionsTests {


    private ResearchRequest researchRequest;
    private ResearchService researchService;


    @BeforeEach
    void init(){
        researchRequest = new ResearchRequest();
        researchRequest.setContent("This is some content for extra suggestions");
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient mockClient = Mockito.mock(WebClient.class);
        ObjectMapper dummyMapper = new ObjectMapper();
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
}

