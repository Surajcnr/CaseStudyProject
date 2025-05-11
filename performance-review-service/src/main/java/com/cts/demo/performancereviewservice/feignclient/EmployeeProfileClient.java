package com.cts.demo.performancereviewservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.performancereviewservice.dto.EmployeeProfileDTO;

@FeignClient(name = "EMPLOYEE-PROFILE-SERVICE",path="/employeeprofiles")
public interface EmployeeProfileClient {
    @GetMapping("/fetchById/{id}")
    EmployeeProfileDTO getById(@PathVariable("id") Long id);
}
