package com.cts.demo.performancereviewservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.demo.performancereviewservice.exceptions.PerformanceNotFound;
import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.service.PerformanceReviewService;

@RestController
@RequestMapping("/performancereviews")
public class PerformanceReviewController {

    @Autowired
    PerformanceReviewService service;

    @PostMapping("/save")
    public PerformanceReview create(@RequestBody @Validated PerformanceReview obj) throws PerformanceNotFound {
        return service.create(obj);
    }

    @GetMapping("/fetchAll")
    public List<PerformanceReview> getAll() {
        return service.getAll();
    }

    @GetMapping("/fetchById/{id}")
    public PerformanceReview getById(@PathVariable Long id) throws PerformanceNotFound{
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public PerformanceReview update(@PathVariable Long id, @RequestBody PerformanceReview obj) {
        return service.update(id, obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @DeleteMapping("/deleteReviewsByEmployeeId/{employeeId}")
    public void deleteReviewsByEmployeeId(@PathVariable Long employeeId) {
        service.deleteReviewsByEmployeeId(employeeId);
    }
    @GetMapping("/fetchReviewsByEmployeeId/{employeeId}")
    public List<PerformanceReview> getReviewsByEmployeeId(@PathVariable Long employeeId) {
        return service.getReviewsByEmployeeId(employeeId);
    }

}
