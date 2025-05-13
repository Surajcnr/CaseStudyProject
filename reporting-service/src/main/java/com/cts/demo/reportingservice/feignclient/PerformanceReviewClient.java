package com.cts.demo.reportingservice.feignclient;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.cts.demo.reportingservice.dto.PerformanceReviewDTO;

@FeignClient(name = "PERFORMANCE-REVIEW-SERVICE", path = "/performancereviews")
public interface PerformanceReviewClient {

    @GetMapping("/fetchReviewsByEmployeeId/{employeeId}")
    List<PerformanceReviewDTO> getReviewsByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
