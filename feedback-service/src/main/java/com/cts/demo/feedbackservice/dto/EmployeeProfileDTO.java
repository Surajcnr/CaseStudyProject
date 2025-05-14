package com.cts.demo.feedbackservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmployeeProfileDTO {
	
    private Long employeeId;
    private String name;
    private String department;
    private String role;
    private String contactDetails;
    // Getters and Setters
}
