package com.cts.demo.performancereviewservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview {

    @Id
    @GeneratedValue
    private Long reviewId;

    @NotNull(message = "Employee Id cannot be null")
    private Long employeeId;

    @NotNull(message = "Manager Id cannot be null")
    private Long managerId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Performance score is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Performance score must be at least 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Performance score must not exceed 100")
    private Double performanceScore;

    @Size(max = 1000, message = "Feedback cannot exceed 1000 characters")
    private String feedback;
}
