package com.research.assistant.JunitServiceTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Services.ResearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ResearchServiceSummarizeTests {
    private ResearchRequest researchRequest;
    private ResearchService researchService;
    private ObjectMapper dummyMapper;


    @BeforeEach
    void init(){
        researchRequest = new ResearchRequest();
        researchRequest.setContent("This is some article to summarize");
        WebClient.Builder mockBuilder = Mockito.mock(WebClient.Builder.class);
        WebClient mockClient = Mockito.mock(WebClient.class);
        dummyMapper = new ObjectMapper();
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
        String action = researchService.getAction();
        assertEquals("summarize", action);
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
        String action = researchService.getAction();
        assertEquals("summarize", action);
        assertEquals(expectedPrompt, actualPrompt);
    }

    @Test
    public void buildPromptThrowIllegalExceptionCase(){
        researchRequest.setOperation("Illegal Summarization");
        assertThrows(IllegalArgumentException.class, () -> researchService.buildPrompt(researchRequest));

    }

    @Test
    public void extractTestFromResponseSummary(){
        String response = "{\n" +
                "    \"candidates\": [\n" +
                "        {\n" +
                "            \"content\": {\n" +
                "                \"parts\": [\n" +
                "                    {\n" +
                "                        \"text\": \"Manchester City achieved a historic Treble in the 2022/23 season, winning the Premier League, FA Cup, and Champions League. Domestically, they overcame a slow start to dominate the Premier League, showcasing their attacking prowess and tactical flexibility. They then defeated Manchester United in the FA Cup final. The crowning achievement was their Champions League victory, defeating Inter Milan in the final, finally securing the elusive European title under Pep Guardiola. This cemented their place as one of the greatest teams in football history.\\n\"\n" +
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
                "    \"responseId\": \"TEST\"\n" +
                "}";
        String actualResponse = researchService.extractTextFromResponse(response);
        String expectedResponse = "Manchester City achieved a historic Treble in the 2022/23 season, winning the Premier League, FA Cup, and Champions League. Domestically, they overcame a slow start to dominate the Premier League, showcasing their attacking prowess and tactical flexibility. They then defeated Manchester United in the FA Cup final. The crowning achievement was their Champions League victory, defeating Inter Milan in the final, finally securing the elusive European title under Pep Guardiola. This cemented their place as one of the greatest teams in football history.\n";
        assertEquals(expectedResponse, actualResponse);
    }

}

