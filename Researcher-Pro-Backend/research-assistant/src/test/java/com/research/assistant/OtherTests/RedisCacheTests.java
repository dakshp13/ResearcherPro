package com.research.assistant.OtherTests;


import com.research.assistant.Model.ResearchAction;
import com.research.assistant.Repositories.ResearchActionRepository;
import com.research.assistant.Repositories.ResearchRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@Testcontainers
@AutoConfigureMockMvc
public class RedisCacheTests {

    @Container
    @ServiceConnection
    static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:7.4.2"))
            .withExposedPorts(6379);

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6:0");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResearchActionRepository researchActionRepository;

    @Autowired
    private ResearchRequestRepository researchRequestRepository;

    @MockitoSpyBean
    private ResearchActionRepository researchActionRepositorySpy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        researchActionRepository.deleteAll();
    }

    @Test
    void testGetStatsAndVerifyCache() throws Exception {
        ResearchAction researchAction1 = new ResearchAction("summarize", 0, "06/16/2025");
        ResearchAction researchAction2 = new ResearchAction("suggest", 0, "06/16/2025");
        ResearchAction researchAction3 = new ResearchAction("Citation", 0, "06/16/2025");
        researchActionRepository.save(researchAction1);
        researchActionRepository.save(researchAction2);
        researchActionRepository.save(researchAction3);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/research/getstats"))
                .andExpect(status().isOk());


        Mockito.verify(researchActionRepositorySpy, Mockito.times(1)).findFirstByAction("summarize");
        Mockito.verify(researchActionRepositorySpy, Mockito.times(1)).findFirstByAction("suggest");
        Mockito.verify(researchActionRepositorySpy, Mockito.times(1)).findFirstByAction("Citation");

        Mockito.clearInvocations(researchActionRepositorySpy);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/research/getstats"))
                .andExpect(status().isOk());

        Mockito.verify(researchActionRepositorySpy, Mockito.times(0)).findFirstByAction("summarize");
        Mockito.verify(researchActionRepositorySpy, Mockito.times(0)).findFirstByAction("suggest");
        Mockito.verify(researchActionRepositorySpy, Mockito.times(0)).findFirstByAction("Citation");

    }




}
