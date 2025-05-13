package com.cts.demo.performancereviewservice.service;

import com.cts.demo.performancereviewservice.exceptions.PerformanceNotFound;
import com.cts.demo.performancereviewservice.model.PerformanceReview;
import java.util.List;

public interface PerformanceReviewService {
    PerformanceReview create(PerformanceReview obj) throws PerformanceNotFound;
    List<PerformanceReview> getAll();
    PerformanceReview getById(Long id);
    PerformanceReview update(Long id, PerformanceReview obj);
    void delete(Long id);
    void deleteReviewsByEmployeeId(Long employeeId);
}
