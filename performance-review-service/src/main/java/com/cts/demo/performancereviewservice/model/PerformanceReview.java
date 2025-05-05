
package com.cts.demo.performancereviewservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long employeeId;
    private Long managerId;
    private LocalDate date;
    private Double performanceScore;

    @Column(length = 1000)
    private String feedback;
}
