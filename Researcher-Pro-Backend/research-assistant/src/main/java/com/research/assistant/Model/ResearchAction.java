package com.research.assistant.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "activity-stats")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchAction {
    @Id
    private ObjectId id;
    private String action;
    private int totalCount;
    private String lastTimeAccessed;
    @DocumentReference
    private List<ResearchRequest> researchRequestList;

    public void addToResearchRequestList(ResearchRequest researchRequest){
        this.researchRequestList.add(researchRequest);
    }

}
