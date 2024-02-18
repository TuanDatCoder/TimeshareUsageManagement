package com.FTimeshare.UsageManagement.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {
    @GetMapping("/person")
    public String getPerson(Model model){


        return "index";
    }

}
