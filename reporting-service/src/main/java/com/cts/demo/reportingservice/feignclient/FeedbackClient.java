package com.cts.demo.reportingservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.reportingservice.dto.FeedbackDTO;

@FeignClient(name = "FEEDBACK-SERVICE", path = "/feedbacks")
public interface FeedbackClient {

    @GetMapping("/fetchFeedbacksByEmployeeId/{employeeId}")
    List<FeedbackDTO> getFeedbacksByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
