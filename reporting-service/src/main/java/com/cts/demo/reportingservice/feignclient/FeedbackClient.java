package com.cts.demo.reportingservice.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.reportingservice.dto.FeedbackDTO;

@FeignClient(name = "feedback-service",url="http://localhost:8083/feedbacks")
public interface FeedbackClient {
    @GetMapping("/fetchById/{toEmployeeId}")
    List<FeedbackDTO> getFeedbackByEmployeeId(@PathVariable("toEmployeeId") Long id);
}


