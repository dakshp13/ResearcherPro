package com.research.assistant.Repositories;

import com.research.assistant.Model.ResearchRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ResearchRequestRepository extends MongoRepository<ResearchRequest, ObjectId> {

}
