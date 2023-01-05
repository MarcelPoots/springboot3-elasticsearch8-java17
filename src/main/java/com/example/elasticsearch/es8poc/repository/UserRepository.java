package com.example.elasticsearch.es8poc.repository;

import com.example.elasticsearch.es8poc.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends ElasticsearchRepository<User, Long> {

    List<User> findByFirstName(String firstName);

}
