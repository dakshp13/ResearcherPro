package com.research.assistant.MockitoAPITests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.Controllers.ResearchController;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Services.ResearchService;
import com.research.assistant.Services.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResearchController.class)
public class RateLimiterTests {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private ResearchService researchService;

    @MockitoBean
    private StatsService statsService;


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRateLimiterOnGetStats_Returns429() throws Exception{
        for(int i = 0; i < 5; i++) {
            if(i == 0) {
                mockMvc.perform(get("/api/research/getstats"))
                        .andExpect(status().isOk());
            }
            else{
                mockMvc.perform(get("/api/research/getstats"))
                        .andExpect(status().is(429));
            }
        }
    }

    @Test
    void testRateLimiterOnProcess_Returns429() throws Exception{
        ResearchRequest request = new ResearchRequest();
        request.setOperation("summarize:detailed");
        request.setContent("This is a test content for detailed summary.");

        for(int i = 0; i < 20; i++) {
            if(i < 10) {

                mockMvc.perform(post("/api/research/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
            }
            else{

                mockMvc.perform(post("/api/research/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().is(429));
            }
        }
    }
}
