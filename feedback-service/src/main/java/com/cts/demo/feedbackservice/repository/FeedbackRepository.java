package com.cts.demo.feedbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cts.demo.feedbackservice.model.Feedback;

import jakarta.transaction.Transactional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	@Transactional
    @Modifying
	void deleteByFromEmployeeIdOrToEmployeeId(Long fromEmployeeId, Long toEmployeeId);
}
