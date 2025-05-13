package com.cts.demo.reportingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.demo.reportingservice.model.Report;
import com.cts.demo.reportingservice.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    ReportService service;

    @PostMapping("/save")
    public Report generateEmployeeReport(@RequestBody Report obj) {
        return service.create(obj);
    }

    @GetMapping("/fetchAll")
    public List<Report> getAll() {
        return service.getAll();
    }

    @GetMapping("/fetchById/{id}")
    public Report getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public Report update(@PathVariable Long id, @RequestBody Report obj) {
        return service.update(id, obj);
    }

    @DeleteMapping("deleteById/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    
    @DeleteMapping("/deleteByEmployeeId/{employeeId}")
    public void deleteReportsByEmployeeId(@PathVariable Long employeeId) {
        service.deleteByEmployeeId(employeeId);
    }
}
