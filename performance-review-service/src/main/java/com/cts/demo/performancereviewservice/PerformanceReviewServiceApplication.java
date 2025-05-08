package com.cts.demo.performancereviewservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class PerformanceReviewServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceReviewServiceApplication.class, args);
    }
}
