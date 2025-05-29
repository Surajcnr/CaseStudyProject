package com.cts.demo.employeeprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication //@Configuration, @EnableAutoConfiguration,@ComponentScan.
@EnableFeignClients
public class EmployeeProfileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeProfileServiceApplication.class, args);
    }
}
