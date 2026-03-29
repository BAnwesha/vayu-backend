package com.weather.vayu_backend;

import com.weather.vayu_backend.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimitingService pricingPlanService;

    public RateLimitInterceptor(RateLimitingService pricingPlanService) {
        this.pricingPlanService = pricingPlanService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Identify the user by their IP address
        String ipAddress = request.getRemoteAddr();

        // 2. Get their personal bucket
        Bucket tokenBucket = pricingPlanService.resolveBucket(ipAddress);

        // 3. Try to consume 1 token
        if (tokenBucket.tryConsume(1)) {
            // Success! Token consumed, let the request pass.
            return true;
        } else {
            // Failure! Bucket is empty. Block the request.
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().append("Too many requests. Please try again in a minute.");
            return false;
        }
    }
}
