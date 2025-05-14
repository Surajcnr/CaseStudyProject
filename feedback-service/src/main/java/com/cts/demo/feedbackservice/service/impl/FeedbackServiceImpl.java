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

    @Override
    public List<Feedback> getAll() {
        logger.info("Fetching all feedbacks");
        List<Feedback> feedbacks = repository.findAll();
        logger.info("Fetched {} feedback(s)", feedbacks.size());
        return feedbacks;
    }

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

    @Override
    public void delete(Long id) {
        logger.info("Deleting feedback with id: {}", id);
        repository.deleteById(id);
        logger.info("Deleted feedback with id: {} from repository", id);
    }

    @Override
    public void deleteFeedbacksByEmployeeId(Long employeeId) {
        logger.info("Deleting feedbacks for employee id: {}", employeeId);
        repository.deleteByFromEmployeeIdOrToEmployeeId(employeeId, employeeId);
        logger.info("Deleted feedbacks from repository for employee id: {}", employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
        logger.info("Requested deletion of reports for employee id: {}", employeeId);
    }

    @Override
    public List<Feedback> getFeedbacksByEmployeeId(Long employeeId) {
        logger.info("Fetching feedbacks for employee id: {}", employeeId);
        List<Feedback> feedbacks = repository.findByToEmployeeId(employeeId);
        logger.info("Fetched {} feedback(s) for employee id: {}", feedbacks.size(), employeeId);
        return feedbacks;
    }
}
