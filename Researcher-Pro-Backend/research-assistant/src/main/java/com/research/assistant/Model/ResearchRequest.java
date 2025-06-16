package com.research.assistant.Model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "research-requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchRequest {
    @Id
    private ObjectId id;
    private String content;
    private String operation;

    public ResearchRequest(String content, String operation){
        this.content = content;
        this.operation = operation;
    }


}
