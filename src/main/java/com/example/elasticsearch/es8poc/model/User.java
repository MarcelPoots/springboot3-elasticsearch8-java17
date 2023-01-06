package com.example.elasticsearch.es8poc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Document(indexName = "user", createIndex = true)
public class User {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;


}
