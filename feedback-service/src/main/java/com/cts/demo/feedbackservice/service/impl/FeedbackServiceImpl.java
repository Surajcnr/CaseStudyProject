package com.cts.demo.feedbackservice.service.impl;

import com.cts.demo.feedbackservice.model.Feedback;
import com.cts.demo.feedbackservice.repository.FeedbackRepository;
import com.cts.demo.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    @Override
    public Feedback create(Feedback obj) {
        return repository.save(obj);
    }

    @Override
    public List<Feedback> getAll() {
        return repository.findAll();
    }

    @Override
    public Feedback getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Feedback update(Long id, Feedback obj) {
        if (!repository.existsById(id)) return null;
        obj.setFeedbackId(id);
        return repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
