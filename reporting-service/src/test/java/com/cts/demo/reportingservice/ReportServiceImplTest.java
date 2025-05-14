package com.cts.demo.reportingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.demo.reportingservice.dto.FeedbackDTO;
import com.cts.demo.reportingservice.dto.PerformanceReviewDTO;
import com.cts.demo.reportingservice.feignclient.FeedbackClient;
import com.cts.demo.reportingservice.feignclient.PerformanceReviewClient;
import com.cts.demo.reportingservice.model.Report;
import com.cts.demo.reportingservice.repository.ReportRepository;
import com.cts.demo.reportingservice.service.impl.ReportServiceImpl;

class ReportServiceImplTest {

    @Mock
    private ReportRepository repository;

    @Mock
    private FeedbackClient feedbackClient;

    @Mock
    private PerformanceReviewClient performanceReviewClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Report report;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        report = new Report(1L, 101L, LocalDate.now(), "Avg Score: 4.5", "Good teamwork");
    }

    @Test
    void testCreate_Success() {
        PerformanceReviewDTO reviewDTO = new PerformanceReviewDTO();
        reviewDTO.setPerformanceScore(4.5);
        List<PerformanceReviewDTO> mockReviews = Arrays.asList(reviewDTO);

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setComments("Good teamwork");
        List<FeedbackDTO> mockFeedbacks = Arrays.asList(feedbackDTO);

        when(performanceReviewClient.getReviewsByEmployeeId(101L)).thenReturn(mockReviews);
        when(feedbackClient.getFeedbacksByEmployeeId(101L)).thenReturn(mockFeedbacks);
        when(repository.save(any(Report.class))).thenReturn(report);

        Report created = reportService.create(report);

        assertNotNull(created);
        assertEquals("Average Score: 4.5", created.getPerformanceSummary());
    }


    @Test
    void testCreate_Failure_NoReviews() {
        when(performanceReviewClient.getReviewsByEmployeeId(101L)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> reportService.create(report));
        assertEquals("No performance reviews found for employee id 101", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(report));

        List<Report> reports = reportService.getAll();
        assertFalse(reports.isEmpty());
        assertEquals(1, reports.size());
    }

    @Test
    void testGetById() {
        when(repository.findById(1L)).thenReturn(Optional.of(report));

        Report found = reportService.getById(1L);
        assertNotNull(found);
        assertEquals("Good teamwork", found.getFeedbackSummary());
    }

    @Test
    void testUpdate_Success() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Report.class))).thenReturn(report);

        Report updated = reportService.update(1L, report);
        assertNotNull(updated);
        assertEquals("Avg Score: 4.5", updated.getPerformanceSummary());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);

        reportService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByEmployeeId() {
        when(repository.findAll()).thenReturn(Arrays.asList(report));

        reportService.deleteByEmployeeId(101L);

        verify(repository, times(1)).delete(any(Report.class));
    }
}

