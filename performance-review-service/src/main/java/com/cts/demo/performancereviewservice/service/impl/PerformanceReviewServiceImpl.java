package com.cts.demo.performancereviewservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.performancereviewservice.dto.EmployeeProfileDTO;
import com.cts.demo.performancereviewservice.exceptions.PerformanceNotFound;
import com.cts.demo.performancereviewservice.feignclient.EmployeeProfileClient;
import com.cts.demo.performancereviewservice.feignclient.ReportClient;
import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.repository.PerformanceReviewRepository;
import com.cts.demo.performancereviewservice.service.PerformanceReviewService;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceReviewServiceImpl.class);

    @Autowired
    PerformanceReviewRepository repository;

    @Autowired
    private EmployeeProfileClient employeeProfileClient;

    @Autowired
    private ReportClient reportClient;
    
    @Override
    public PerformanceReview create(PerformanceReview obj) throws PerformanceNotFound {
        logger.info("Attempting to create PerformanceReview for employeeId: {}", obj.getEmployeeId());
        EmployeeProfileDTO employee = employeeProfileClient.getById(obj.getEmployeeId());
        if (employee == null) {
            logger.error("Employee with id {} not found. Aborting creation.", obj.getEmployeeId());
            throw new PerformanceNotFound("Employee not found");
        }
        PerformanceReview created = repository.save(obj);
        logger.info("Created PerformanceReview with id: {}", created.getReviewId());
        return created;
    }

    @Override
    public List<PerformanceReview> getAll() {
        logger.info("Fetching all PerformanceReviews");
        List<PerformanceReview> reviews = repository.findAll();
        logger.info("Found {} performance reviews", reviews.size());
        return reviews;
    }

    @Override
    public PerformanceReview getById(Long id) {
        logger.info("Fetching PerformanceReview with id: {}", id);
        PerformanceReview review = repository.findById(id).orElse(null);
        if (review == null) {
            logger.warn("PerformanceReview with id {} not found", id);
        } else {
            logger.info("Found PerformanceReview: {}", review);
        }
        return review;
    }

    @Override
    public PerformanceReview update(Long id, PerformanceReview obj) {
        logger.info("Attempting to update PerformanceReview with id: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("PerformanceReview with id {} does not exist. Update aborted.", id);
            return null;
        }
        obj.setReviewId(id);
        PerformanceReview updated = repository.save(obj);
        logger.info("Updated PerformanceReview with id: {}", id);
        return updated;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting PerformanceReview with id: {}", id);
        repository.deleteById(id);
        logger.info("Deleted PerformanceReview with id: {}", id);
    }

    @Override
    public void deleteReviewsByEmployeeId(Long employeeId) {
        logger.info("Deleting all PerformanceReviews for employeeId: {}", employeeId);
        repository.deleteByEmployeeId(employeeId);
        logger.info("Deleted PerformanceReviews from repository for employeeId: {}", employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
        logger.info("Requested deletion of reports for employeeId: {}", employeeId);
    }

    @Override
    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        logger.info("Fetching PerformanceReviews for employee with id: {}", employeeId);
        List<PerformanceReview> reviews = repository.findByEmployeeId(employeeId);
        logger.info("Found {} PerformanceReviews for employeeId: {}", reviews.size(), employeeId);
        return reviews;
    }
}
