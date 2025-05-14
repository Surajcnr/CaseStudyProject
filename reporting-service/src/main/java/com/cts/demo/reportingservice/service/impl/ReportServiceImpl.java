package com.cts.demo.reportingservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportRepository repository;

    @Autowired
    private FeedbackClient feedbackClient;

    @Autowired
    private PerformanceReviewClient performanceReviewClient;

    @Override
    public Report create(Report obj) {
        logger.info("Creating report for employee id: {}", obj.getEmployeeId());
        
        List<PerformanceReviewDTO> reviews = performanceReviewClient.getReviewsByEmployeeId(obj.getEmployeeId());
        if (reviews == null || reviews.isEmpty()) {
            logger.error("No performance reviews found for employee id: {}", obj.getEmployeeId());
            throw new RuntimeException("No performance reviews found for employee id " + obj.getEmployeeId());
        }
        
        double avgScore = reviews.stream()
                .mapToDouble(PerformanceReviewDTO::getPerformanceScore)
                .average()
                .orElse(0);
        obj.setPerformanceSummary("Average Score: " + avgScore);
        logger.info("Calculated average score: {}", avgScore);

        List<FeedbackDTO> feedbacks = feedbackClient.getFeedbacksByEmployeeId(obj.getEmployeeId());
        String aggregatedComments = "";
        if (feedbacks != null && !feedbacks.isEmpty()) {
            aggregatedComments = feedbacks.stream()
                    .map(FeedbackDTO::getComments)
                    .collect(Collectors.joining("\n"));
            logger.info("Aggregated feedback comments for employee id {}: {}", obj.getEmployeeId(), aggregatedComments);
        } else {
            logger.info("No feedback available for employee id: {}", obj.getEmployeeId());
        }
        obj.setFeedbackSummary(aggregatedComments);

        obj.setReportDate(LocalDate.now());
        Report savedReport = repository.save(obj);
        logger.info("Report saved with id: {}", savedReport.getReportId());
        return savedReport;
    }

    @Override
    public List<Report> getAll() {
        logger.info("Fetching all reports");
        List<Report> reports = repository.findAll();
        logger.info("Fetched {} reports", reports.size());
        return reports;
    }

    @Override
    public Report getById(Long id) {
        logger.info("Fetching report with id: {}", id);
        Report report = repository.findById(id).orElse(null);
        if (report == null) {
            logger.warn("No report found for id: {}", id);
        } else {
            logger.info("Found report: {}", report);
        }
        return report;
    }

    @Override
    public Report update(Long id, Report obj) {
        logger.info("Updating report with id: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Report with id {} not found. Update aborted.", id);
            return null;
        }
        obj.setReportId(id);
        Report updatedReport = repository.save(obj);
        logger.info("Report successfully updated with id: {}", id);
        return updatedReport;
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting report with id: {}", id);
        repository.deleteById(id);
        logger.info("Report with id: {} deleted from repository", id);
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        logger.info("Deleting all reports for employee id: {}", employeeId);
        List<Report> reports = repository.findAll().stream()
                                .filter(r -> r.getEmployeeId().equals(employeeId))
                                .collect(Collectors.toList());
        for (Report report : reports) {
            repository.delete(report);
            logger.info("Deleted report with id: {} for employee id: {}", report.getReportId(), employeeId);
        }
    }
}
