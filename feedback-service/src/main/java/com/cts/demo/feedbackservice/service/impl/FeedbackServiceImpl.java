package com.cts.demo.feedbackservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.feedbackservice.feignclient.EmployeeProfileClient;
import com.cts.demo.feedbackservice.feignclient.ReportClient;
import com.cts.demo.feedbackservice.model.Feedback;
import com.cts.demo.feedbackservice.repository.FeedbackRepository;
import com.cts.demo.feedbackservice.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository repository;
    
    @Autowired
    private EmployeeProfileClient employeeProfileClient; 

    @Autowired
    private ReportClient reportClient;


    @Override
    public Feedback create(Feedback obj) {
        if (employeeProfileClient.getEmployeeById(obj.getFromEmployeeId()) == null) {
            throw new RuntimeException("From employee with ID " + obj.getFromEmployeeId() + " not found");
        }
        if (employeeProfileClient.getEmployeeById(obj.getToEmployeeId()) == null) {
            throw new RuntimeException("To employee with ID " + obj.getToEmployeeId() + " not found");
        }
        return repository.save(obj);
    }

    @Override
    public List<Feedback> getAll() {
        return repository.findAll();
    }

    @Override
    public Feedback getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Feedback update(Long id, Feedback obj) {
        if (!repository.existsById(id)) return null;

        if (employeeProfileClient.getEmployeeById(obj.getFromEmployeeId()) == null) {
            throw new RuntimeException("From employee with ID " + obj.getFromEmployeeId() + " not found");
        }
        if (employeeProfileClient.getEmployeeById(obj.getToEmployeeId()) == null) {
            throw new RuntimeException("To employee with ID " + obj.getToEmployeeId() + " not found");
        }
        obj.setFeedbackId(id);
        return repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    @Override
    public void deleteFeedbacksByEmployeeId(Long employeeId) {
        repository.deleteByFromEmployeeIdOrToEmployeeId(employeeId, employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
    }
    @Override
    public List<Feedback> getFeedbacksByEmployeeId(Long employeeId) {
        return repository.findByToEmployeeId(employeeId);
    }
    

}
