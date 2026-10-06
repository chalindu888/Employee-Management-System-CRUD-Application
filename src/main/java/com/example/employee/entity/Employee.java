package com.example.employee.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Clients can read the ID, but cannot set it through JSON.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    @Column(nullable = false, length = 254)
    private String email;

    @Size(max = 100, message = "Department must be at most 100 characters")
    @Column(length = 100)
    private String department;

    // BigDecimal avoids floating-point rounding errors for money.
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    @Digits(
        integer = 10,
        fraction = 2,
        message = "Salary allows up to 10 integer digits and 2 decimal places"
    )
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    // JPA and Jackson require a no-argument constructor.
    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
