package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository repository;

    // Constructor injection makes the dependency explicit.
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Employee create(Employee employee) {
        employee.setId(null); // Always insert a new employee.
        return repository.save(employee);
    }

    public List<Employee> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Transactional
    public Employee update(Long id, Employee input) {
        Employee existing = findById(id); // Missing record becomes HTTP 404.

        existing.setName(input.getName());
        existing.setEmail(input.getEmail());
        existing.setDepartment(input.getDepartment());
        existing.setSalary(input.getSalary());

        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}