package com.example.fibonacci.service;

import com.example.fibonacci.log.Loggable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class FibonacciService {

    private final Map<Integer, BigInteger> memo = new ConcurrentHashMap<>();

    @Loggable
    public BigInteger fibonacci(String input) {
        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be a valid integer.", e);
        }

        if (index < 0)
            throw new IllegalArgumentException("Index cannot be negative");

        return compute(index);
    }

    private BigInteger compute(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        // 💥 DO NOT use computeIfAbsent here
        if (memo.containsKey(n)) return memo.get(n);

        BigInteger result = compute(n - 1).add(compute(n - 2));
        memo.put(n, result);
        return result;
    }
}
