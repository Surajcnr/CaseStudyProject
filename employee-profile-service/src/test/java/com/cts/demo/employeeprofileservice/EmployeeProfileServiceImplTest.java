package com.cts.demo.employeeprofileservice;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.cts.demo.employeeprofileservice.feignclient.FeedbackClient;
import com.cts.demo.employeeprofileservice.feignclient.PerformanceReviewClient;
import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import com.cts.demo.employeeprofileservice.repository.EmployeeProfileRepository;
import com.cts.demo.employeeprofileservice.service.impl.EmployeeProfileServiceImpl;

class EmployeeProfileServiceImplTest {

    @Mock
    private EmployeeProfileRepository repository;

    @Mock
    private PerformanceReviewClient performanceReviewClient;

    @Mock
    private FeedbackClient feedbackClient;

    @InjectMocks
    private EmployeeProfileServiceImpl employeeProfileService;

    private EmployeeProfile employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new EmployeeProfile(1L, "John Doe", "IT", "Developer", "john.doe@example.com");
    }

    @Test
    void testCreate() {
        when(repository.save(any(EmployeeProfile.class))).thenReturn(employee);
        EmployeeProfile created = employeeProfileService.create(employee);
        assertNotNull(created);
        assertEquals("John Doe", created.getName());
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(employee));
        List<EmployeeProfile> profiles = employeeProfileService.getAll();
        assertFalse(profiles.isEmpty());
        assertEquals(1, profiles.size());
    }

    @Test
    void testGetById() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        EmployeeProfile found = employeeProfileService.getById(1L);
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    void testUpdate() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(EmployeeProfile.class))).thenReturn(employee);
        EmployeeProfile updated = employeeProfileService.update(1L, employee);
        assertNotNull(updated);
        assertEquals("John Doe", updated.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(repository).deleteById(1L);
        doNothing().when(performanceReviewClient).deleteReviewsByEmployeeId(1L);
        doNothing().when(feedbackClient).deleteFeedbacksByEmployeeId(1L);
        
        employeeProfileService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
        verify(performanceReviewClient, times(1)).deleteReviewsByEmployeeId(1L);
        verify(feedbackClient, times(1)).deleteFeedbacksByEmployeeId(1L);
    }
}

