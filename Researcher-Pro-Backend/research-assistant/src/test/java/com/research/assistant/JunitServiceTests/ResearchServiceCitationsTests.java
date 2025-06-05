package com.research.assistant.JunitServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.ResearchRequest;
import com.research.assistant.ResearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;




public class ResearchServiceCitationsTests {
    private ResearchRequest researchRequest;
    private ResearchService researchService;


    @BeforeEach
    void init(){
        researchRequest = new ResearchRequest();
        researchRequest.setContent("https://www.nationalgeographic.com/environment/article/global-warming-effects");
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient mockClient = Mockito.mock(WebClient.class);
        ObjectMapper dummyMapper = new ObjectMapper();
        Mockito.when(mockBuilder.build()).thenReturn(mockClient);
        researchService = new ResearchService(mockBuilder, dummyMapper);
    }


    @Test
    public void buildPromptForAPA(){
        researchRequest.setOperation("APA Citation");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "If " + "https://www.nationalgeographic.com/environment/article/global-warming-effects" + " looks like a valid citable link then cite it using this" +
                "format: " + "APA Citation" + ", otherwise just state that its not a citable link. Once again here is the link:\n\n";
        expectedPrompt += "https://www.nationalgeographic.com/environment/article/global-warming-effects";
        assertEquals(expectedPrompt, actualPrompt);
    }


    @Test
    public void buildPromptForMLA(){
        researchRequest.setOperation("MLA Citation");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "If " + "https://www.nationalgeographic.com/environment/article/global-warming-effects" + " looks like a valid citable link then cite it using this" +
                "format: " + "MLA Citation" + ", otherwise just state that its not a citable link. Once again here is the link:\n\n";
        expectedPrompt += "https://www.nationalgeographic.com/environment/article/global-warming-effects";
        assertEquals(expectedPrompt, actualPrompt);
    }


    @Test
    public void buildPromptForIEEE(){
        researchRequest.setOperation("IEEE Citation");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "If " + "https://www.nationalgeographic.com/environment/article/global-warming-effects" + " looks like a valid citable link then cite it using this" +
                "format: " + "IEEE Citation" + ", otherwise just state that its not a citable link. Once again here is the link:\n\n";
        expectedPrompt += "https://www.nationalgeographic.com/environment/article/global-warming-effects";
        assertEquals(expectedPrompt, actualPrompt);
    }


    @Test
    public void buildPromptForChicagoStyle(){
        researchRequest.setOperation("Chicago-Style Citation");
        String actualPrompt = researchService.buildPrompt(researchRequest);
        String expectedPrompt = "If " + "https://www.nationalgeographic.com/environment/article/global-warming-effects" + " looks like a valid citable link then cite it using this" +
                "format: " + "Chicago-Style Citation" + ", otherwise just state that its not a citable link. Once again here is the link:\n\n";
        expectedPrompt += "https://www.nationalgeographic.com/environment/article/global-warming-effects";
        assertEquals(expectedPrompt, actualPrompt);
    }


}
