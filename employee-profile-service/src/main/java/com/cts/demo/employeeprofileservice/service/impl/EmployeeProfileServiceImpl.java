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

    /**
     * Creates a new EmployeeProfile and saves it in the repository.
     * Logs the creation process and returns the created EmployeeProfile.
     *
     * @param obj the EmployeeProfile object to create.
     * @return the created EmployeeProfile with its assigned id.
     */
    @Override
    public EmployeeProfile create(EmployeeProfile obj) {
        logger.info("Creating EmployeeProfile: {}", obj);
        EmployeeProfile createdProfile = repository.save(obj);
        logger.info("Created EmployeeProfile with id: {}", createdProfile.getEmployeeId());
        return createdProfile;
    }

    /**
     * Retrieves all EmployeeProfiles from the repository.
     * Logs the number of profiles fetched.
     *
     * @return a list of all EmployeeProfiles.
     */
    @Override
    public List<EmployeeProfile> getAll() {
        logger.info("Fetching all EmployeeProfiles");
        List<EmployeeProfile> profiles = repository.findAll();
        logger.info("Fetched {} EmployeeProfiles", profiles.size());
        return profiles;
    }

    /**
     * Fetches an EmployeeProfile by its id from the repository.
     * Logs a warning if the profile is not found.
     *
     * @param id the id of the EmployeeProfile to retrieve.
     * @return the EmployeeProfile if found, otherwise null.
     */
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

    /**
     * Updates an existing EmployeeProfile with new data.
     * First verifies if the profile exists; if not, logs a warning and returns null.
     *
     * @param id  the id of the EmployeeProfile to update.
     * @param obj the EmployeeProfile object containing updated data.
     * @return the updated EmployeeProfile object, or null if update failed.
     */
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

    /**
     * Deletes an EmployeeProfile by its id.
     * Also triggers deletion of associated performance reviews and feedbacks via Feign clients.
     *
     * @param id the id of the EmployeeProfile to delete.
     */
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
