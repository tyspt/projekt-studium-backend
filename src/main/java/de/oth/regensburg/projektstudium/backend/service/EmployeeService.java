package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findOneById(Long id);

    Employee addEmployee(Employee newEmployee);

    Employee updateEmployee(Employee newEmployee);

    Employee deleteEmployee(Long employeeId);
}
