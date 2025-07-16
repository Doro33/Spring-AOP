package com.example.fibonacci.controller;

import com.example.fibonacci.service.FibonacciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fibonacci")
public class FibonacciController {
    private final FibonacciService service;
    @Autowired
    public FibonacciController(FibonacciService service) {
        this.service = service;
    }

    @GetMapping("/{index}")
    public String fibonacci(@PathVariable String index) {
        return service.fibonacci(index).toString();
    }
}
