package com.cts.demo.reportingservice.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.demo.reportingservice.dto.FeedbackDTO;
import com.cts.demo.reportingservice.dto.PerformanceReviewDTO;
import com.cts.demo.reportingservice.feignclient.FeedbackClient;
import com.cts.demo.reportingservice.feignclient.PerformanceReviewClient;
import com.cts.demo.reportingservice.model.Report;
import com.cts.demo.reportingservice.repository.ReportRepository;
import com.cts.demo.reportingservice.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository repository;
	
	@Autowired
	private FeedbackClient feedbackClient;

	@Autowired
	private PerformanceReviewClient performanceReviewClient;

	@Override
	public Report generateEmployeeReport(Long employeeId) {
		List<FeedbackDTO> feedbacks = feedbackClient.getFeedbackByEmployeeId(employeeId);
		List<PerformanceReviewDTO> reviews = performanceReviewClient.getReviewsByEmployeeId(employeeId);

		// logic to create Report entity using feedbacks and reviews
		Report report = new Report();
		report.setEmployeeId(employeeId);
		report.setReportDate(LocalDate.now());
		report.setFeedbackSummary("Feedback count: " + feedbacks.size());
		report.setPerformanceSummary("Reviews count: " + reviews.size());

		return repository.save(report);
	}

	@Override
	public List<Report> getAll() {
		return repository.findAll();
	}

	@Override
	public Report getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Report update(Long id, Report obj) {
		if (!repository.existsById(id))
			return null;
		obj.setReportId(id);
		return repository.save(obj);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
