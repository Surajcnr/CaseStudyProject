package com.cts.demo.reportingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue
    private Long reportId;

    @NotNull(message = "Employee id is required")
    private Long employeeId;
    
    @NotNull(message = "Report date is required")
    private LocalDate reportDate;

    @NotBlank(message = "Performance summary is required")
    @Size(max = 1000, message = "Performance summary must not exceed 1000 characters")
    @Column(length = 1000)
    private String performanceSummary;

    @NotBlank(message = "Feedback summary is required")
    @Size(max = 1000, message = "Feedback summary must not exceed 1000 characters")
    @Column(length = 1000)
    private String feedbackSummary;
}
