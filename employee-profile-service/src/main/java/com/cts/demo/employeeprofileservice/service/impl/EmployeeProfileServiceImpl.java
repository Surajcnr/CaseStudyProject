package com.cts.demo.employeeprofileservice.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.employeeprofileservice.feignclient.FeedbackClient;
import com.cts.demo.employeeprofileservice.feignclient.PerformanceReviewClient;
import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import com.cts.demo.employeeprofileservice.repository.EmployeeProfileRepository;
import com.cts.demo.employeeprofileservice.service.EmployeeProfileService;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeProfileServiceImpl.class);
    
    @Autowired
    private EmployeeProfileRepository repository;
    
    @Autowired
    private PerformanceReviewClient performanceReviewClient;
    
    @Autowired
    private FeedbackClient feedbackClient;

    @Override
    public EmployeeProfile create(EmployeeProfile obj) {
        logger.info("Creating EmployeeProfile: {}", obj);
        EmployeeProfile createdProfile = repository.save(obj);
        logger.info("Created EmployeeProfile with id: {}", createdProfile.getEmployeeId());
        return createdProfile;
    }

    @Override
    public List<EmployeeProfile> getAll() {
        logger.info("Fetching all EmployeeProfiles");
        List<EmployeeProfile> profiles = repository.findAll();
        logger.info("Fetched {} EmployeeProfiles", profiles.size());
        return profiles;
    }

    @Override
    public EmployeeProfile getById(Long id) {
        logger.info("Fetching EmployeeProfile with id: {}", id);
        EmployeeProfile profile = repository.findById(id).orElse(null);
        if (profile == null) {
            logger.warn("EmployeeProfile with id {} not found", id);
        } else {
            logger.info("Found EmployeeProfile: {}", profile);
        }
        return profile;
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile obj) {
        logger.info("Attempting to update EmployeeProfile with id: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("EmployeeProfile with id {} does not exist; update failed", id);
            return null;
        }
        obj.setEmployeeId(id);
        EmployeeProfile updatedProfile = repository.save(obj);
        logger.info("Updated EmployeeProfile with id: {}", id);
        return updatedProfile;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting EmployeeProfile with id: {}", id);
        repository.deleteById(id);
        logger.info("Deleted EmployeeProfile from repository with id: {}", id);
        performanceReviewClient.deleteReviewsByEmployeeId(id);
        logger.info("Requested deletion of performance reviews for EmployeeProfile id: {}", id);
        feedbackClient.deleteFeedbacksByEmployeeId(id);
        logger.info("Requested deletion of feedbacks for EmployeeProfile id: {}", id);
    }
}
