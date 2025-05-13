package com.cts.demo.employeeprofileservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.employeeprofileservice.feignclient.FeedbackClient;
import com.cts.demo.employeeprofileservice.feignclient.PerformanceReviewClient;
import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import com.cts.demo.employeeprofileservice.repository.EmployeeProfileRepository;
import com.cts.demo.employeeprofileservice.service.EmployeeProfileService;

@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    @Autowired
    private EmployeeProfileRepository repository;
    
    @Autowired
    private PerformanceReviewClient performanceReviewClient;
    
    @Autowired
    private FeedbackClient feedbackClient;

    @Override
    public EmployeeProfile create(EmployeeProfile obj) {
        return repository.save(obj);
    }

    @Override
    public List<EmployeeProfile> getAll() {
        return repository.findAll();
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile obj) {
        if (!repository.existsById(id)) 
        	return null;
        obj.setEmployeeId(id);
        return repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        performanceReviewClient.deleteReviewsByEmployeeId(id);
        feedbackClient.deleteFeedbacksByEmployeeId(id);
    }
}
