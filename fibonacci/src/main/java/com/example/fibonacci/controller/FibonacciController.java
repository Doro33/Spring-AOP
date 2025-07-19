package com.example.fibonacci.controller;

import com.example.fibonacci.service.FibonacciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fibonacci")
public class FibonacciController {
    private final FibonacciService service;

    @Autowired
    public FibonacciController(FibonacciService service) {
        this.service = service;
    }

    @GetMapping("/{input}")
    public String fibonacci(@PathVariable String input) {
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be a valid integer.", e);
        }
        return service.fibonacci(index).toString();
    }

    @DeleteMapping("/{input}")
    public ResponseEntity<String> evict(@PathVariable String input) {
        service.evictAndNotify(input);
        return ResponseEntity.ok("Evicted " + input);
    }

}
