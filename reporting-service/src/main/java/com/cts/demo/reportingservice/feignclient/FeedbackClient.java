package com.cts.demo.reportingservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.reportingservice.dto.FeedbackDTO;

@FeignClient(name = "feedback-service")
public interface FeedbackClient {
    @GetMapping("/api/feedbacks/employee/{employeeId}")
    List<FeedbackDTO> getFeedbackByEmployeeId(@PathVariable("employeeId") Long employeeId);
}


