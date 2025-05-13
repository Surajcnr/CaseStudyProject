package com.cts.demo.employeeprofileservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FEEDBACK-SERVICE", path = "/feedbacks")
public interface FeedbackClient {
    
    @DeleteMapping("/deleteFeedbacksByEmployeeId/{employeeId}")
    void deleteFeedbacksByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
