package com.apitest.demo.controller;

import com.apitest.demo.TrackExecutionTime;
import com.apitest.demo.model.User;
import com.apitest.demo.repository.UserRepositoryCustom;
import com.apitest.demo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepositoryCustom userRepository;


    /**
     * Use to find user by id
     *
     * @param id of the user
     * @return the user if he exist / return not found if not (404)
     */
    @GetMapping("/{id}")
    @TrackExecutionTime
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Use to find user by username
     *
     * @param username of the user
     * @return the user if he exist / return not found if not (404)
     */
    @GetMapping("findByUsername/{username}")
    @TrackExecutionTime
    public ResponseEntity<User> findUserByUsername(@PathVariable(value = "username") String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Use to add new user
     * Need to be adult and from France
     * Need to be a new username
     * @param user new user to add
     * @return bad request if parameters not correct / return the id number if insert
     */
    @PostMapping("/addUser")
    @TrackExecutionTime
    public ResponseEntity<String> addUser(@Validated @RequestBody User user) {

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("username cannot be null/empty");
        }
        if (user.getBirthdate() == null || user.getBirthdate().isEmpty()) {
            return ResponseEntity.badRequest().body("birthdate cannot be null/empty");
        }
        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            return ResponseEntity.badRequest().body("country cannot be null/empty");
        }
        if (!user.getCountry().equals("France")) {
            return ResponseEntity.badRequest().body("Only French users are accepted");
        }
        if (!DateUtils.isIsoDate(user.getBirthdate())) {
            return ResponseEntity.badRequest().body("Wrong date format: Use yyyy-MM-ddTHH:mm:ss.SSSZ");
        }
        if (!DateUtils.isAnAdult(user.getBirthdate())) {
            return ResponseEntity.badRequest().body("Only adult allowed");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist");
        }
        User userCreated = userRepository.save(user);
        return ResponseEntity.ok("User created - ID : " + userCreated.getId());
    }


}
