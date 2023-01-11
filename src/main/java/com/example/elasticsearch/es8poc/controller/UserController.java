package com.example.elasticsearch.es8poc.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.example.elasticsearch.es8poc.model.User;
import com.example.elasticsearch.es8poc.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    ElasticsearchClient client;


    @PostConstruct
    public void init() {

        List<User> users = new ArrayList<>();
        users.add(new User(1l,"John", "Smith", "M"));
        users.add(new User(2l,"Jane", "Doe", "V"));
        repository.saveAll(users);
        System.err.println("Users saved");

    }

    @GetMapping("/users")
    public String getUsers() {
        var users = new StringBuilder();
        repository.findAll().forEach(user -> users.append(user.toString() + "\n"));
        return users.toString();
    }

    @GetMapping("/users/{firstName}")
    public String getUserByFirstName(@PathVariable final String firstName) {
        var users = new StringBuilder();
        repository.findByFirstName(firstName).forEach(user -> users.append(user.toString() + "\n"));
        return users.toString();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUserByFirstName(@PathVariable final long id) {
        repository.deleteById(id);
        return "User with id " + id + " deleted";
    }

    @GetMapping("/users/advanced/{searchstring}")
    public String getAdvancedResults(@PathVariable final String searchstring) throws IOException {

        SearchResponse<User> searchResponse = client.search(s -> s
                        .index("user")
                        .query(q -> q
                                .match(t -> t
                                        .field("firstName")
                                        .query(searchstring)
                                )
                        ),
                User.class
        );

        TotalHits total = searchResponse.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            System.out.println("There are " + total.value() + " results for value " + searchstring);
        } else {
            System.out.println("There are more than " + total.value() + " results for value " + searchstring);
        }

        List<Hit<User>> hits = searchResponse.hits().hits();
        for (Hit<User> hit: hits) {
            User user = hit.source();
            System.out.println(("Found user " + user.getFirstName() + ", score " + hit.score()));
        }

        return "OK";
    }

    @GetMapping("/users/api/{id}")
    public User getUserViaApi(@PathVariable final String id) throws IOException {

        GetResponse<User> response = client.get(g -> g
                        .index("user")
                        .id(id),
                User.class
        );

        if (response.found()) {
            User user = response.source();
            System.out.println("User name " + user.getFirstName());
            return user;
        } else {
            System.out.println("User not found.");
            return null;
        }
    }

}
