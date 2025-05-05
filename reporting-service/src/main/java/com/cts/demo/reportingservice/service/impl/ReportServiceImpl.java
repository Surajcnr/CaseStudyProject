package com.cts.demo.reportingservice.service.impl;

import com.cts.demo.reportingservice.model.Report;
import com.cts.demo.reportingservice.repository.ReportRepository;
import com.cts.demo.reportingservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository repository;

    @Override
    public Report create(Report obj) {
        return repository.save(obj);
    }

    @Override
    public List<Report> getAll() {
        return repository.findAll();
    }

    @Override
    public Report getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Report update(Long id, Report obj) {
        if (!repository.existsById(id)) return null;
        obj.setReportId(id);
        return repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
