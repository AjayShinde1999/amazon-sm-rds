package com.awssmrds.controller;

import com.awssmrds.entity.User;
import com.awssmrds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveOneUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> fetchAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User fetchById(@PathVariable long id) {
        return userService.getUserById(id);
    }
}
