package com.cts.demo.performancereviewservice.service.impl;

import com.cts.demo.performancereviewservice.model.PerformanceReview;
import com.cts.demo.performancereviewservice.repository.PerformanceReviewRepository;
import com.cts.demo.performancereviewservice.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    @Autowired
    private PerformanceReviewRepository repository;

    @Override
    public PerformanceReview create(PerformanceReview obj) {
        return repository.save(obj);
    }

    @Override
    public List<PerformanceReview> getAll() {
        return repository.findAll();
    }

    @Override
    public PerformanceReview getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PerformanceReview update(Long id, PerformanceReview obj) {
        if (!repository.existsById(id)) return null;
        obj.setReviewId(id);
        return repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
