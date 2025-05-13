package com.cts.demo.feedbackservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.feedbackservice.dto.EmployeeProfileDTO;


@FeignClient(name = "EMPLOYEE-PROFILE-SERVICE", path = "/employeeprofiles")
public interface EmployeeProfileClient {
    
    @GetMapping("/fetchById/{id}")
    EmployeeProfileDTO getEmployeeById(@PathVariable("id") Long id);
}
