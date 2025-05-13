package com.cts.demo.feedbackservice.service;

import com.cts.demo.feedbackservice.model.Feedback;
import java.util.List;

public interface FeedbackService {
    Feedback create(Feedback obj);
    List<Feedback> getAll();
    Feedback getById(Long id);
    Feedback update(Long id, Feedback obj);
    void delete(Long id);
	void deleteFeedbacksByEmployeeId(Long employeeId);
	List<Feedback> getFeedbacksByEmployeeId(Long employeeId);

}
