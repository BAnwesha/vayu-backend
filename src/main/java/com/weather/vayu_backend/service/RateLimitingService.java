package com.weather.vayu_backend.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitingService {

    // A thread-safe map to store a bucket for each user/IP address
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String ipAddress) {
        return cache.computeIfAbsent(ipAddress, this::newBucket);
    }

    private Bucket newBucket(String ipAddress) {
        // Rule: Refill 20 tokens every 1 minute
        Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
        // Rule: The bucket can hold a maximum of 20 tokens at a time
        Bandwidth limit = Bandwidth.classic(20, refill);

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}