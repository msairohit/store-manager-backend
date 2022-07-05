package com.rohit.storemanager.controllers;

import com.rohit.storemanager.models.ApiResponse;
import com.rohit.storemanager.models.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/method")
    public String test() {
        return "working";
    }

    @GetMapping("/null")
    public String nullMethod() {
        throw new NullPointerException();
    }
}
