package com.cts.demo.performancereviewservice;

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

import com.cts.demo.performancereviewservice.dto.EmployeeProfileDTO;
import com.cts.demo.performancereviewservice.exceptions.PerformanceNotFound;
import com.cts.demo.performancereviewservice.feignclient.EmployeeProfileClient;
import com.cts.demo.performancereviewservice.feignclient.ReportClient;
import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.repository.PerformanceReviewRepository;
import com.cts.demo.performancereviewservice.service.impl.PerformanceReviewServiceImpl;

class PerformanceReviewServiceImplTest {

    @Mock
    private PerformanceReviewRepository repository;

    @Mock
    private EmployeeProfileClient employeeProfileClient;

    @Mock
    private ReportClient reportClient;

    @InjectMocks
    private PerformanceReviewServiceImpl performanceReviewService;

    private PerformanceReview review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new PerformanceReview(1L, 101L, 201L, LocalDate.of(2025, 5, 10), 4.5, "Excellent performance!");
    }

    @Test
    void testCreate_Success() throws PerformanceNotFound {
        when(employeeProfileClient.getById(101L)).thenReturn(new EmployeeProfileDTO());
        when(repository.save(any(PerformanceReview.class))).thenReturn(review);

        PerformanceReview created = performanceReviewService.create(review);
        assertNotNull(created);
        assertEquals(4.5, created.getPerformanceScore());
    }

    @Test
    void testCreate_Failure_EmployeeNotFound() {
        when(employeeProfileClient.getById(101L)).thenReturn(null);

        Exception exception = assertThrows(PerformanceNotFound.class, () -> performanceReviewService.create(review));
        assertEquals("Employee not found", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(review));

        List<PerformanceReview> reviews = performanceReviewService.getAll();
        assertFalse(reviews.isEmpty());
        assertEquals(1, reviews.size());
    }

    @Test
    void testGetById() throws PerformanceNotFound {
        when(repository.findById(1L)).thenReturn(Optional.of(review));

        PerformanceReview found = performanceReviewService.getById(1L);
        assertNotNull(found);
        assertEquals(4.5, found.getPerformanceScore());
    }

    @Test
    void testUpdate_Success() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(PerformanceReview.class))).thenReturn(review);

        PerformanceReview updated = performanceReviewService.update(1L, review);
        assertNotNull(updated);
        assertEquals("Excellent performance!", updated.getFeedback());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);

        performanceReviewService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReviewsByEmployeeId() {
        doNothing().when(repository).deleteByEmployeeId(101L);
        doNothing().when(reportClient).deleteReportsByEmployeeId(101L);

        performanceReviewService.deleteReviewsByEmployeeId(101L);

        verify(repository, times(1)).deleteByEmployeeId(101L);
        verify(reportClient, times(1)).deleteReportsByEmployeeId(101L);
    }
}

