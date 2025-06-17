package com.research.assistant.DBTests;



import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Repositories.ResearchRequestRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
public class ResearchRequestRepositoryTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6:0");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ResearchRequestRepository researchRequestRepository;

    @Test
    public void testUpdatingMongoDB_Once(){
        ResearchRequest researchRequest = new ResearchRequest("Testing Content","Testing Operation");
        researchRequestRepository.insert(researchRequest);
        researchRequestRepository.save(researchRequest);
        assertTrue(!researchRequestRepository.findAll().isEmpty());
        assertTrue(researchRequestRepository.findAll().contains(researchRequest));

        researchRequestRepository.delete(researchRequest);

        assertEquals(0,researchRequestRepository.findAll().size());
        assertTrue(!researchRequestRepository.findAll().contains(researchRequest));
    }

    @Test
    public void testUpdatingMongoDB_Many(){
        List<ResearchRequest> researchRequestList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            ResearchRequest researchRequest = new ResearchRequest("Testing Content","Testing Operation");
            researchRequestList.add(researchRequest);
            researchRequestRepository.insert(researchRequest);
            researchRequestRepository.save(researchRequest);
        }

        assertTrue(!researchRequestRepository.findAll().isEmpty());
        assertEquals(researchRequestList, researchRequestRepository.findAll());

        for(int i = 0; i < 10; i++){
            assertEquals(researchRequestList.get(i),researchRequestRepository.findAll().get(i));
        }
        assertEquals(10, researchRequestRepository.findAll().size());
    }

    @AfterEach
    void cleanup(){
        researchRequestRepository.deleteAll();
    }

}
