package com.example.barber_appointment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class DenemeController {


    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }
}
