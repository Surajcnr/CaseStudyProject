package com.cts.demo.feedbackservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.feedbackservice.feignclient.EmployeeProfileClient;
import com.cts.demo.feedbackservice.feignclient.ReportClient;
import com.cts.demo.feedbackservice.model.Feedback;
import com.cts.demo.feedbackservice.repository.FeedbackRepository;
import com.cts.demo.feedbackservice.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    
    @Autowired
    private FeedbackRepository repository;
    
    @Autowired
    private EmployeeProfileClient employeeProfileClient;
    
    @Autowired
    private ReportClient reportClient;

    /**
     * Creates a new Feedback record.
     * <p>
     * This method validates that both the "from" and "to" employee IDs exist
     * by invoking the EmployeeProfileClient. If either is missing, a RuntimeException
     * is thrown. Otherwise, the feedback is saved to the repository.
     * </p>
     *
     * @param obj the Feedback object to be created.
     * @return the created Feedback object containing its assigned ID.
     * @throws RuntimeException if the "from" or "to" employee cannot be found.
     */
    @Override
    public Feedback create(Feedback obj) {
        logger.info("Creating feedback: {}", obj);
        if (employeeProfileClient.getEmployeeById(obj.getFromEmployeeId()) == null) {
            logger.error("From employee with ID {} not found", obj.getFromEmployeeId());
            throw new RuntimeException("From employee with ID " + obj.getFromEmployeeId() + " not found");
        }
        if (employeeProfileClient.getEmployeeById(obj.getToEmployeeId()) == null) {
            logger.error("To employee with ID {} not found", obj.getToEmployeeId());
            throw new RuntimeException("To employee with ID " + obj.getToEmployeeId() + " not found");
        }
        Feedback createdFeedback = repository.save(obj);
        logger.info("Created feedback with id: {}", createdFeedback.getFeedbackId());
        return createdFeedback;
    }

    /**
     * Retrieves all feedback records from the repository.
     *
     * @return a List of all Feedback objects.
     */
    @Override
    public List<Feedback> getAll() {
        logger.info("Fetching all feedbacks");
        List<Feedback> feedbacks = repository.findAll();
        logger.info("Fetched {} feedback(s)", feedbacks.size());
        return feedbacks;
    }

    /**
     * Retrieves a Feedback record by its ID.
     *
     * @param id the ID of the feedback to retrieve.
     * @return the Feedback object if found, or null if not present.
     */
    @Override
    public Feedback getById(Long id) {
        logger.info("Fetching feedback with id: {}", id);
        Feedback feedback = repository.findById(id).orElse(null);
        if (feedback == null) {
            logger.warn("Feedback with id {} not found", id);
        } else {
            logger.info("Found feedback: {}", feedback);
        }
        return feedback;
    }

    /**
     * Updates an existing Feedback record.
     * <p>
     * The method first checks if a Feedback record with the provided ID exists.
     * It also verifies that both the "from" and "to" employee IDs are valid.
     * If the record exists and validations pass, the feedback is updated.
     * </p>
     *
     * @param id  the ID of the Feedback to update.
     * @param obj the Feedback object containing the updated information.
     * @return the updated Feedback object, or null if the record does not exist.
     * @throws RuntimeException if the "from" or "to" employee cannot be found.
     */
    @Override
    public Feedback update(Long id, Feedback obj) {
        logger.info("Updating feedback with id: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Feedback with id {} does not exist. Update aborted.", id);
            return null;
        }
        if (employeeProfileClient.getEmployeeById(obj.getFromEmployeeId()) == null) {
            logger.error("From employee with ID {} not found", obj.getFromEmployeeId());
            throw new RuntimeException("From employee with ID " + obj.getFromEmployeeId() + " not found");
        }
        if (employeeProfileClient.getEmployeeById(obj.getToEmployeeId()) == null) {
            logger.error("To employee with ID {} not found", obj.getToEmployeeId());
            throw new RuntimeException("To employee with ID " + obj.getToEmployeeId() + " not found");
        }
        obj.setFeedbackId(id);
        Feedback updatedFeedback = repository.save(obj);
        logger.info("Updated feedback with id: {}", id);
        return updatedFeedback;
    }

    /**
     * Deletes a Feedback record by its ID.
     *
     * @param id the ID of the Feedback to delete.
     */
    @Override
    public void delete(Long id) {
        logger.info("Deleting feedback with id: {}", id);
        repository.deleteById(id);
        logger.info("Deleted feedback with id: {} from repository", id);
    }

    /**
     * Deletes all Feedback records associated with a particular employee ID.
     * <p>
     * This method deletes feedbacks where the given employee ID appears either as
     * the sender or receiver, and then requests the deletion of related reports.
     * </p>
     *
     * @param employeeId the ID of the employee whose feedbacks are to be deleted.
     */
    @Override
    public void deleteFeedbacksByEmployeeId(Long employeeId) {
        logger.info("Deleting feedbacks for employee id: {}", employeeId);
        repository.deleteByFromEmployeeIdOrToEmployeeId(employeeId, employeeId);
        logger.info("Deleted feedbacks from repository for employee id: {}", employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
        logger.info("Requested deletion of reports for employee id: {}", employeeId);
    }

    /**
     * Retrieves all Feedback records addressed to a specific employee.
     *
     * @param employeeId the ID of the employee whose feedbacks are to be retrieved.
     * @return a List of Feedback objects addressed to the specified employee.
     */
    @Override
    public List<Feedback> getFeedbacksByEmployeeId(Long employeeId) {
        logger.info("Fetching feedbacks for employee id: {}", employeeId);
        List<Feedback> feedbacks = repository.findByToEmployeeId(employeeId);
        logger.info("Fetched {} feedback(s) for employee id: {}", feedbacks.size(), employeeId);
        return feedbacks;
    }
}
