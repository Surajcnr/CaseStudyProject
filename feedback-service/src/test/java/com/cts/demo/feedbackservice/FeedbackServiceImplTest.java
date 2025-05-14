package com.cts.demo.feedbackservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.demo.feedbackservice.dto.EmployeeProfileDTO;
import com.cts.demo.feedbackservice.feignclient.EmployeeProfileClient;
import com.cts.demo.feedbackservice.feignclient.ReportClient;
import com.cts.demo.feedbackservice.model.Feedback;
import com.cts.demo.feedbackservice.repository.FeedbackRepository;
import com.cts.demo.feedbackservice.service.impl.FeedbackServiceImpl;

class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository repository;

    @Mock
    private EmployeeProfileClient employeeProfileClient;

    @Mock
    private ReportClient reportClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback feedback;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feedback = new Feedback(1L, 101L, 102L, "Positive", "Great work!");
    }

    @Test
    void testCreate_Success() {
        when(employeeProfileClient.getEmployeeById(101L)).thenReturn(new EmployeeProfileDTO());
        when(employeeProfileClient.getEmployeeById(102L)).thenReturn(new EmployeeProfileDTO());
        when(repository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback created = feedbackService.create(feedback);
        assertNotNull(created);
        assertEquals("Great work!", created.getComments());
    }

    @Test
    void testCreate_Failure_MissingEmployee() {
        when(employeeProfileClient.getEmployeeById(101L)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.create(feedback));
        assertEquals("From employee with ID 101 not found", exception.getMessage());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(feedback));

        List<Feedback> feedbacks = feedbackService.getAll();
        assertFalse(feedbacks.isEmpty());
        assertEquals(1, feedbacks.size());
    }

    @Test
    void testGetById() {
        when(repository.findById(1L)).thenReturn(Optional.of(feedback));

        Feedback found = feedbackService.getById(1L);
        assertNotNull(found);
        assertEquals("Positive", found.getFeedbackType());
    }

    @Test
    void testUpdate_Success() {
        when(repository.existsById(1L)).thenReturn(true);
        when(employeeProfileClient.getEmployeeById(101L)).thenReturn(new EmployeeProfileDTO());
        when(employeeProfileClient.getEmployeeById(102L)).thenReturn(new EmployeeProfileDTO());
        when(repository.save(any(Feedback.class))).thenReturn(feedback);

        Feedback updated = feedbackService.update(1L, feedback);
        assertNotNull(updated);
        assertEquals("Great work!", updated.getComments());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);

        feedbackService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteFeedbacksByEmployeeId() {
        doNothing().when(repository).deleteByFromEmployeeIdOrToEmployeeId(101L, 101L);
        doNothing().when(reportClient).deleteReportsByEmployeeId(101L);

        feedbackService.deleteFeedbacksByEmployeeId(101L);

        verify(repository, times(1)).deleteByFromEmployeeIdOrToEmployeeId(101L, 101L);
        verify(reportClient, times(1)).deleteReportsByEmployeeId(101L);
    }
}

