package com.cts.demo.performancereviewservice.controller;

import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performancereviews")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService service;

    @PostMapping
    public PerformanceReview create(@RequestBody PerformanceReview obj) {
        return service.create(obj);
    }

    @GetMapping
    public List<PerformanceReview> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PerformanceReview getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PerformanceReview update(@PathVariable Long id, @RequestBody PerformanceReview obj) {
        return service.update(id, obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
