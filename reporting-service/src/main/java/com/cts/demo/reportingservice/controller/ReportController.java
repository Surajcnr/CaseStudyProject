package com.cts.demo.reportingservice.controller;

import com.cts.demo.reportingservice.model.Report;
import com.cts.demo.reportingservice.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @PostMapping("/save")
    public Report create(@RequestBody Report obj) {
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
}
