package com.FTimeshare.UsageManagement.controllers;

import com.FTimeshare.UsageManagement.entities.UserEntity;
import com.FTimeshare.UsageManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping("/role/owner")
    @ResponseBody
    public List<UserEntity> getUsersByRoleOwner() {
        System.out.print(userService.getUsersByRole("owner"));
        return userService.getUsersByRole("owner");
    }

    @GetMapping("/")
    @ResponseBody
    public String hello(){
        return "hello";
    }

    // Other controller methods
}
