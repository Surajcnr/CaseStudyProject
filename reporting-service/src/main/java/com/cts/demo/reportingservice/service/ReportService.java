package com.cts.demo.reportingservice.service;

import com.cts.demo.reportingservice.model.Report;
import java.util.List;

public interface ReportService {
	Report create(Report obj); 
    List<Report> getAll();
    Report getById(Long id);
    Report update(Long id, Report obj);
    void delete(Long id);
    void deleteByEmployeeId(Long employeeId);
}
