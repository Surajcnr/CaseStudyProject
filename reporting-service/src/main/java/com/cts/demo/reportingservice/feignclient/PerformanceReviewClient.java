package com.cts.demo.reportingservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.reportingservice.dto.PerformanceReviewDTO;

@FeignClient(name = "performance-review-service")
public interface PerformanceReviewClient {
    @GetMapping("/api/performancereviews/employee/{employeeId}")
    List<PerformanceReviewDTO> getReviewsByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
