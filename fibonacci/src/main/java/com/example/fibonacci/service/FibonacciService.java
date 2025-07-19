package com.example.fibonacci.service;

import com.example.fibonacci.log.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class FibonacciService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public FibonacciService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Loggable
    @Cacheable(value = "fibonacci", key = "#input")
    public BigInteger fibonacci(int input) {
        if (input < 0) throw new IllegalArgumentException("Index cannot be negative");
        return computeIterative(input);
    }

    private BigInteger computeIterative(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;

        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            BigInteger next = a.add(b);
            a = b;
            b = next;
        }

        return b;
    }

    @CacheEvict(value = "fibonacci", key = "#input")
    public void evictAndNotify(String input) {
        redisTemplate.convertAndSend("cache-invalidation", "fibonacci::" + input);
    }

}

