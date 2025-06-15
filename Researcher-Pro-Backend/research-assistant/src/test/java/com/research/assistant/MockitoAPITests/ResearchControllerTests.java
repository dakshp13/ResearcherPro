package com.research.assistant.MockitoAPITests;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.research.assistant.Controllers.ResearchController;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Services.ResearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ResearchController.class)
public class ResearchControllerTests {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private ResearchService researchService;


    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void givenDetailedSummaryRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("summarize:detailed");
        request.setContent("This is a test content for detailed summary.");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked detailed summary response");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }


    @Test
    void givenBriefSummaryRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("summarize:brief");
        request.setContent("Short summary content.");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked brief summary response");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void givenSuggestionRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("suggest");
        request.setContent("Topic for suggestions.");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked suggestion response");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void givenAPACitationRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("APA Citation");
        request.setContent("https://example.com/article");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked APA citation");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void givenMLACitationRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("MLA Citation");
        request.setContent("https://example.com/article");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked MLA citation");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void givenIEEECitationRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("IEEE Citation");
        request.setContent("https://example.com/article");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked IEEE citation");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }


    @Test
    void givenChicagoStyleCitationRequest_whenPost_thenReturnsOk() throws Exception {
        ResearchRequest request = new ResearchRequest();
        request.setOperation("Chicago-Style Citation");
        request.setContent("https://example.com/article");


        when(researchService.processContent(any(ResearchRequest.class)))
                .thenReturn("Mocked Chicago-Style citation");


        mockMvc.perform(post("/api/research/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }








}
