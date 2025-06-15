package com.research.assistant.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
