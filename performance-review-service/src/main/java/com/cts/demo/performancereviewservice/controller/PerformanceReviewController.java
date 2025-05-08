package com.cts.demo.performancereviewservice.controller;

import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performancereviews")
public class PerformanceReviewController {

    @Autowired
    PerformanceReviewService service;

    @PostMapping("/save")
    public PerformanceReview create(@RequestBody PerformanceReview obj) {
        return service.create(obj);
    }

    @GetMapping("/fetchAll")
    public List<PerformanceReview> getAll() {
        return service.getAll();
    }

    @GetMapping("/fetchById/{id}")
    public PerformanceReview getById(@PathVariable Long id) {
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
}
