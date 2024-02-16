package com.FTimeshare.UsageManagement.controllers;

import ch.qos.logback.core.model.Model;
import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

//    @RequestMapping ("/all")
//    public List<UserEntity> getUsers(){
//        List<UserEntity> userList = userService.getUsers();
//        return userList;
//    }
@GetMapping("/all")
public ResponseEntity<List<UserEntity>> getUsers(){

    return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
}
}