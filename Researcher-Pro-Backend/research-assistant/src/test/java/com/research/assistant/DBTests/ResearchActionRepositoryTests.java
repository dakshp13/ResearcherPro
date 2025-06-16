package com.research.assistant.DBTests;


import com.research.assistant.Model.ResearchAction;
import com.research.assistant.Model.ResearchRequest;
import com.research.assistant.Repositories.ResearchActionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class ResearchActionRepositoryTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6:0");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry){
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private ResearchActionRepository researchActionRepository;

    @Test
    public void testFindFirstByAction_Summarize(){
        ResearchAction researchAction = new ResearchAction("summarize", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("summarize");
        assertTrue(result.isPresent());

    }

    @Test
    public void testUpdatingMongoDB_Summarize(){
        ResearchAction researchAction = new ResearchAction("summarize", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("summarize");

        if (result.isPresent()) {
            ResearchRequest researchRequest = new ResearchRequest("Content to Summarize","summarize");

            researchAction.setTotalCount(researchAction.getTotalCount()+1);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = today.format(formatter);
            researchAction.setLastTimeAccessed(formattedDate);
            researchAction.addToResearchRequestList(researchRequest);
            researchActionRepository.save(researchAction);

            assertEquals(1,researchAction.getTotalCount());
            assertEquals(formattedDate, researchAction.getLastTimeAccessed());
            assertFalse(researchAction.getResearchRequestList().isEmpty());
            assertEquals(1,researchAction.getResearchRequestList().size());
            assertEquals(researchRequest, researchAction.getResearchRequestList().get(0));


        }
        else {
            assertTrue(false);
        }

    }

    @Test
    public void testFindFirstByAction_Suggest(){
        ResearchAction researchAction = new ResearchAction("suggest", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("suggest");
        assertTrue(result.isPresent());
    }

    @Test
    public void testUpdatingMongoDB_Suggest(){
        ResearchAction researchAction = new ResearchAction("suggest", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("suggest");

        if (result.isPresent()) {
            ResearchRequest researchRequest = new ResearchRequest("Content to Suggest","suggest");

            researchAction.setTotalCount(researchAction.getTotalCount()+1);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = today.format(formatter);
            researchAction.setLastTimeAccessed(formattedDate);
            researchAction.addToResearchRequestList(researchRequest);
            researchActionRepository.save(researchAction);

            assertEquals(1,researchAction.getTotalCount());
            assertEquals(formattedDate, researchAction.getLastTimeAccessed());
            assertFalse(researchAction.getResearchRequestList().isEmpty());
            assertEquals(1,researchAction.getResearchRequestList().size());
            assertEquals(researchRequest, researchAction.getResearchRequestList().get(0));


        }
        else {
            assertTrue(false);
        }

    }

    @Test
    public void testFindFirstByAction_Citation(){
        ResearchAction researchAction = new ResearchAction("Citation", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("Citation");
        assertTrue(result.isPresent());
    }

    @Test
    public void testUpdatingMongoDB_Citation(){
        ResearchAction researchAction = new ResearchAction("citation", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("citation");

        if (result.isPresent()) {
            ResearchRequest researchRequest = new ResearchRequest("Content to Cite","Citation");

            researchAction.setTotalCount(researchAction.getTotalCount()+1);
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = today.format(formatter);
            researchAction.setLastTimeAccessed(formattedDate);
            researchAction.addToResearchRequestList(researchRequest);
            researchActionRepository.save(researchAction);

            assertEquals(1,researchAction.getTotalCount());
            assertEquals(formattedDate, researchAction.getLastTimeAccessed());
            assertFalse(researchAction.getResearchRequestList().isEmpty());
            assertEquals(1,researchAction.getResearchRequestList().size());
            assertEquals(researchRequest, researchAction.getResearchRequestList().get(0));


        }
        else {
            assertTrue(false);
        }

    }

    @Test
    public void testFindFirstByAction_InvalidAction(){
        ResearchAction researchAction = new ResearchAction("Action", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("InvalidAction");
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdatingMongoDB_InvalidActionAndNotExisting(){
        ResearchAction researchAction = new ResearchAction("Action", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("InvalidAction");

        if (result.isPresent()) {
            fail();
        }
        else {
            assertTrue(true);
        }

    }

    @Test
    public void testUpdatingMongoDB_ExistingAndMultipleRequests(){
        ResearchAction researchAction = new ResearchAction("action", 0, "06/16/2025", new ArrayList<>());
        researchActionRepository.save(researchAction);
        Optional<ResearchAction> result = researchActionRepository.findFirstByAction("action");

        if(result.isPresent()){
            String lastDateAccessed = "";
            List<ResearchRequest> expectedResearchRequests = new ArrayList<>();

            for(int i = 0; i < 10; i++) {
                ResearchRequest researchRequest = new ResearchRequest("content", "action");
                researchAction.setTotalCount(researchAction.getTotalCount() + 1);
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String formattedDate = today.format(formatter);
                lastDateAccessed = formattedDate;
                researchAction.setLastTimeAccessed(formattedDate);
                researchAction.addToResearchRequestList(researchRequest);
                expectedResearchRequests.add(researchRequest);
            }

            researchActionRepository.save(researchAction);

            assertEquals(10,researchAction.getTotalCount());
            assertEquals(lastDateAccessed, researchAction.getLastTimeAccessed());
            assertFalse(researchAction.getResearchRequestList().isEmpty());
            assertEquals(10,researchAction.getResearchRequestList().size());
            assertEquals(expectedResearchRequests, researchAction.getResearchRequestList());

        }
        else{
            fail();
        }
    }




}
