package com.research.assistant.Repositories;

import com.research.assistant.Model.ResearchAction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResearchActionRepository extends MongoRepository<ResearchAction, ObjectId> {
    Optional<ResearchAction> findFirstByAction(String action);
}
