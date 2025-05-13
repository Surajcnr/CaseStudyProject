package com.cts.demo.feedbackservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "REPORTING-SERVICE", path = "/reports")
public interface ReportClient {

    @DeleteMapping("/deleteByEmployeeId/{employeeId}")
    void deleteReportsByEmployeeId(@PathVariable("employeeId") Long employeeId);
}
