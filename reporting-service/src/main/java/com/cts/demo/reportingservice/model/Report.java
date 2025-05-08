
package com.cts.demo.reportingservice.model;

import jakarta.persistence.*;
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

    private Long employeeId;
    private LocalDate reportDate;

    @Column(length = 1000)
    private String performanceSummary;

    @Column(length = 1000)
    private String feedbackSummary;
}
