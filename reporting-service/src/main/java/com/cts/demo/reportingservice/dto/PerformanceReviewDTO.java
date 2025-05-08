package com.cts.demo.reportingservice.dto;

import java.time.LocalDate;

public class PerformanceReviewDTO {
    private Long reviewId;
    private Long employeeId;
    private Long managerId;
    private LocalDate date;
    private Double performanceScore;
    private String feedback;
    // Getters and Setters
}
