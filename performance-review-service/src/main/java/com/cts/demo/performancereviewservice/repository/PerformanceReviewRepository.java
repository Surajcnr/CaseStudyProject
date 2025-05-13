package com.cts.demo.performancereviewservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cts.demo.performancereviewservice.model.PerformanceReview;

import jakarta.transaction.Transactional;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
	@Transactional
    @Modifying
    void deleteByEmployeeId(Long employeeId);
	List<PerformanceReview> findByEmployeeId(Long employeeId);

}
