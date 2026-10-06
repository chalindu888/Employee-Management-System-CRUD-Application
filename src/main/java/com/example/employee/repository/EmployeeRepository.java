package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring supplies save, findAll, findById and delete implementations.
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}