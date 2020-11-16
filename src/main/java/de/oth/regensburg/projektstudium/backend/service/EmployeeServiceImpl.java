package de.oth.regensburg.projektstudium.backend.service;

import de.oth.regensburg.projektstudium.backend.entity.Employee;
import de.oth.regensburg.projektstudium.backend.exceptions.NotFoundException;
import de.oth.regensburg.projektstudium.backend.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> findAll() {
        return repository.findAll();
    }

    @Override
    public Employee findOneById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(Employee.class));
    }

    @Override
    public Employee addEmployee(Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @Override
    public Employee deleteEmployee(Long employeeId) {
        final Employee employee = this.findOneById(employeeId);
        repository.delete(employee);
        return employee;
    }
}
