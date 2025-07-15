package com.example.logging_practice.log;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class InfoLog {
    @Id
    @GeneratedValue
    private Long id;
    private String methodName;
    private String input;
    private String result;
    private LocalDateTime timestamp = LocalDateTime.now();
}

