
package com.cts.demo.feedbackservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue
    private Long feedbackId;

    private Long fromEmployeeId;
    private Long toEmployeeId;
    private String feedbackType;
    
    @Column(length = 1000)
    private String comments;
}
