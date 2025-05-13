package com.cts.demo.employeeprofileservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PERFORMANCE-REVIEW-SERVICE", path = "/performancereviews")
public interface PerformanceReviewClient {
    
    @DeleteMapping("/deleteReviewsByEmployeeId/{employeeId}")
    void deleteReviewsByEmployeeId(@PathVariable("employeeId") Long employeeId);
}