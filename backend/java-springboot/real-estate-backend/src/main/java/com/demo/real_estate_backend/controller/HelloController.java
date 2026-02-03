package com.demo.real_estate_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/")
    public String sayHello() {
        return "Backend is up and running! Ready to list some properties.";
    }

}
