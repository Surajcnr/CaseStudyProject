package com.cts.demo.reportingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class ReportingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportingServiceApplication.class, args);
    }
}
