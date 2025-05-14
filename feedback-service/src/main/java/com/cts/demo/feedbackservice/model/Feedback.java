package com.cts.demo.feedbackservice.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue
    private Long feedbackId;

    @NotNull(message = "The fromEmployeeId cannot be null")
    private Long fromEmployeeId;

    @NotNull(message = "The toEmployeeId cannot be null")
    private Long toEmployeeId;

    @NotBlank(message = "Feedback type is required")
    @Size(max = 50, message = "Feedback type must not exceed 50 characters")
    private String feedbackType;
    
    @Size(max = 1000, message = "Comments cannot exceed 1000 characters")
    private String comments;
}
