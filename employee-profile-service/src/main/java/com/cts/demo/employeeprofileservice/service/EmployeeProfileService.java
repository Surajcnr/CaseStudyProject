package com.cts.demo.employeeprofileservice.service;

import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import java.util.List;

public interface EmployeeProfileService {
    EmployeeProfile create(EmployeeProfile obj);
    List<EmployeeProfile> getAll();
    EmployeeProfile getById(Long id);
    EmployeeProfile update(Long id, EmployeeProfile obj);
    void delete(Long id);
}
