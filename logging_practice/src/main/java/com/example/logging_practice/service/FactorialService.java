package com.example.logging_practice.service;

import com.example.logging_practice.log.Loggable;

import java.math.BigInteger;

@org.springframework.stereotype.Service
public class FactorialService {

    @Loggable
    public BigInteger factorial(String inputNumber) {
        try {
            int n = Integer.parseInt(inputNumber);

            if (n < 0)
                throw new IllegalArgumentException("Negative input: " + n);

            BigInteger result = BigInteger.ONE;
            for (int i = 2; i <= n; i++) {
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: not a number", e);
        }
    }
}
