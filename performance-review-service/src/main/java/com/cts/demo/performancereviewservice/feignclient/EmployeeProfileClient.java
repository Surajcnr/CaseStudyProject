package com.cts.demo.performancereviewservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.demo.performancereviewservice.dto.EmployeeProfileDTO;

@FeignClient(name = "employee-profile-service")
public interface EmployeeProfileClient {
    @GetMapping("/employeeprofiles/{id}")
    EmployeeProfileDTO getEmployeeById(@PathVariable("id") Long id);
}
