package com.example.logging_practice.controller;

import com.example.logging_practice.service.FactorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/factorial")
public class FactorialController {
    private final FactorialService service;

    @Autowired
    public FactorialController(FactorialService service) {
        this.service = service;
    }

    @GetMapping("/{inputNumber}")
    public String factorial(@PathVariable String inputNumber) {
        return service.factorial(inputNumber).toString();
    }
}
