package com.cts.demo.reportingservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
	public Report create(Report obj) {
		List<PerformanceReviewDTO> reviews = performanceReviewClient.getReviewsByEmployeeId(obj.getEmployeeId());
		if (reviews == null || reviews.isEmpty()) {
			throw new RuntimeException("No performance reviews found for employee id " + obj.getEmployeeId());
		}
		double avgScore = reviews.stream().mapToDouble(PerformanceReviewDTO::getPerformanceScore).average().orElse(0);
		obj.setPerformanceSummary("Average Score: " + avgScore);

		List<FeedbackDTO> feedbacks = feedbackClient.getFeedbacksByEmployeeId(obj.getEmployeeId());
		String aggregatedComments = "";
		if (feedbacks != null && !feedbacks.isEmpty()) {
			aggregatedComments = feedbacks.stream().map(FeedbackDTO::getComments).collect(Collectors.joining("\n"));
		}
		obj.setFeedbackSummary(aggregatedComments);

		obj.setReportDate(LocalDate.now());

		return repository.save(obj);
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

	@Override
	public void deleteByEmployeeId(Long employeeId) {
		List<Report> reports = repository.findAll().stream().filter(r -> r.getEmployeeId().equals(employeeId))
				.collect(Collectors.toList());
		for (Report report : reports) {
			repository.delete(report);
		}
	}
}
