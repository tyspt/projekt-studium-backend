package de.oth.regensburg.projektstudium.backend.controller;

import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    List<Employee> findAll() {
        return employeeService.findAll();
    }

    @PostMapping("")
    Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    Employee updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
        employee.setId(id);
        log.info(employee.toString());
        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    Employee deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.deleteEmployee(id);
    }
}
