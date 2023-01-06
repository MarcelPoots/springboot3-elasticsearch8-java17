package com.example.elasticsearch.es8poc.controller;

import com.example.elasticsearch.es8poc.model.User;
import com.example.elasticsearch.es8poc.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void init() {

        List<User> users = new ArrayList<>();
        users.add(new User(1l,"John", "Smith", "M"));
        users.add(new User(2l,"Jane", "Doe", "V"));
        repository.saveAll(users);
        System.err.println("Users saved");

        User changedUser = new User(1l,"John2", "Smith2", "M");
        repository.save(changedUser);
        System.err.println("User changed");

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

}
