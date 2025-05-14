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
    
    /**
     * Creates a new PerformanceReview record.
     * <p>
     * This method verifies that an EmployeeProfile exists for the given employeeId using the EmployeeProfileClient.
     * If the employee does not exist, a PerformanceNotFound exception is thrown.
     * Otherwise, the performance review is saved and returned.
     * </p>
     *
     * @param obj the PerformanceReview object to be created.
     * @return the created PerformanceReview object with its generated reviewId.
     * @throws PerformanceNotFound if the employee associated with the review is not found.
     */
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

    /**
     * Retrieves all PerformanceReview records from the repository.
     *
     * @return a List of all PerformanceReview objects.
     */
    @Override
    public List<PerformanceReview> getAll() {
        logger.info("Fetching all PerformanceReviews");
        List<PerformanceReview> reviews = repository.findAll();
        logger.info("Found {} performance reviews", reviews.size());
        return reviews;
    }

    /**
     * Retrieves a PerformanceReview record by its reviewId.
     *
     * @param id the ID of the PerformanceReview to retrieve.
     * @return the PerformanceReview object if found; otherwise, returns null.
     */
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

    /**
     * Updates an existing PerformanceReview record.
     * <p>
     * Verifies that a review with the given id exists. If it does,
     * sets the reviewId on the provided PerformanceReview object and saves the update.
     * </p>
     *
     * @param id  the ID of the PerformanceReview to update.
     * @param obj the PerformanceReview object containing updated data.
     * @return the updated PerformanceReview object, or null if the review does not exist.
     */
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

    /**
     * Deletes a PerformanceReview record by its reviewId.
     *
     * @param id the ID of the PerformanceReview to delete.
     */
    @Override
    public void delete(Long id) {
        logger.info("Deleting PerformanceReview with id: {}", id);
        repository.deleteById(id);
        logger.info("Deleted PerformanceReview with id: {}", id);
    }

    /**
     * Deletes all PerformanceReview records for a specific employee.
     * <p>
     * This method deletes reviews by employeeId from the repository and then requests
     * the deletion of related reports via the ReportClient.
     * </p>
     *
     * @param employeeId the employeeId for which reviews should be deleted.
     */
    @Override
    public void deleteReviewsByEmployeeId(Long employeeId) {
        logger.info("Deleting all PerformanceReviews for employeeId: {}", employeeId);
        repository.deleteByEmployeeId(employeeId);
        logger.info("Deleted PerformanceReviews from repository for employeeId: {}", employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
        logger.info("Requested deletion of reports for employeeId: {}", employeeId);
    }

    /**
     * Retrieves all PerformanceReview records associated with a specific employee.
     *
     * @param employeeId the employeeId whose reviews are to be fetched.
     * @return a List of PerformanceReview objects for the given employeeId.
     */
    @Override
    public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
        logger.info("Fetching PerformanceReviews for employee with id: {}", employeeId);
        List<PerformanceReview> reviews = repository.findByEmployeeId(employeeId);
        logger.info("Found {} PerformanceReviews for employeeId: {}", reviews.size(), employeeId);
        return reviews;
    }
}
