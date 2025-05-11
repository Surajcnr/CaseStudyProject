package com.cts.demo.reportingservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.reportingservice.dto.PerformanceReviewDTO;

@FeignClient(name = "performance-review-service",url="http://localhost:8082/performancereviews")
public interface PerformanceReviewClient {
    @GetMapping("/fetchById/{employeeId}")
    List<PerformanceReviewDTO> getReviewsByEmployeeId(@PathVariable("employeeId") Long id);
}
