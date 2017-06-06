package com.mywork.springboot.controller;

import com.mywork.springboot.domain.User;
import com.mywork.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET)
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(method = POST)
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(path = "/count", method = GET)
    public long countUsers() {
        return userRepository.count();
    }

    @RequestMapping(path = "/updateAge", method = GET)
    public void updateAgeOfAllUsers() {
        userRepository.findAll().forEach(user -> {
            user.setAge(user.getAge() + 1);
            userRepository.save(user);
        });
    }

    @RequestMapping(path = "/minAge", method = GET)
    public User getMinAgeOfAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().min(Comparator.comparingInt(User::getAge)).orElse(null);
    }
}
