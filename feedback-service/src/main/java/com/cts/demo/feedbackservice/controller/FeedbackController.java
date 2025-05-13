package com.cts.demo.feedbackservice.controller;

import com.cts.demo.feedbackservice.model.Feedback;
import com.cts.demo.feedbackservice.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    FeedbackService service;

    @PostMapping("/save")
    public Feedback create(@RequestBody Feedback obj) {
        return service.create(obj);
    }

    @GetMapping("/fetchAll")
    public List<Feedback> getAll() {
        return service.getAll();
    }

    @GetMapping("/fetchById/{toEmployeeId}")
    public Feedback getById(@PathVariable Long toEmployeeId) {
        return service.getById(toEmployeeId);
    }

    @PutMapping("/update/{id}")
    public Feedback update(@PathVariable Long id, @RequestBody Feedback obj) {
        return service.update(id, obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @DeleteMapping("/deleteFeedbacksByEmployeeId/{employeeId}")
    public void deleteFeedbacksByEmployeeId(@PathVariable Long employeeId) {
        service.deleteFeedbacksByEmployeeId(employeeId);
    }
}
