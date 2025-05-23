package com.cts.demo.employeeprofileservice.repository;

import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
}
