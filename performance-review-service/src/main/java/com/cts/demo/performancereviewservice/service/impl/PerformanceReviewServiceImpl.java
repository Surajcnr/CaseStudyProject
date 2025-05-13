package com.cts.demo.performancereviewservice.service.impl;

import java.util.List;

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

	@Autowired
	PerformanceReviewRepository repository;

	@Autowired
	private EmployeeProfileClient employeeProfileClient;

	@Autowired
	private ReportClient reportClient;
	
	@Override
	public PerformanceReview create(PerformanceReview obj) throws PerformanceNotFound {
		EmployeeProfileDTO employee = employeeProfileClient.getById(obj.getEmployeeId());
		if (employee == null) {
			throw new PerformanceNotFound("Employee not found");
		}
		return repository.save(obj);
	}

	@Override
	public List<PerformanceReview> getAll() {
		return repository.findAll();
	}

	@Override
	public PerformanceReview getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public PerformanceReview update(Long id, PerformanceReview obj) {
		if (!repository.existsById(id))
			return null;
		obj.setReviewId(id);
		return repository.save(obj);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	@Override
    public void deleteReviewsByEmployeeId(Long employeeId) {
        repository.deleteByEmployeeId(employeeId);
        reportClient.deleteReportsByEmployeeId(employeeId);
    }
	@Override
	public List<PerformanceReview> getReviewsByEmployeeId(Long employeeId) {
	    return repository.findByEmployeeId(employeeId);
	}

}
