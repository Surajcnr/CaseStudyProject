package com.cts.demo.employeeprofileservice.controller;

import com.cts.demo.employeeprofileservice.model.EmployeeProfile;
import com.cts.demo.employeeprofileservice.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeprofiles")
public class EmployeeProfileController {

    @Autowired
    private EmployeeProfileService service;

    @PostMapping("/save")
    public EmployeeProfile create(@RequestBody EmployeeProfile obj) {
        return service.create(obj);
    }

    @GetMapping("/fetchAll")
    public List<EmployeeProfile> getAll() {
        return service.getAll();
    }

    @GetMapping("/fetchById/{id}")
    public EmployeeProfile getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public EmployeeProfile update(@PathVariable Long id, @RequestBody EmployeeProfile obj) {
        return service.update(id, obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
