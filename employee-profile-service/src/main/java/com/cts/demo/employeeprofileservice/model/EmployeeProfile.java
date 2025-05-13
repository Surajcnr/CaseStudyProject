
package com.cts.demo.employeeprofileservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile {

    @Id
    
    private Long employeeId;

    private String name;
    private String department;
    private String role;
    private String contactDetails;
}
