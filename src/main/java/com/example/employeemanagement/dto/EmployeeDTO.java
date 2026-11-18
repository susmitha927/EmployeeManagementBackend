
package com.example.employeemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {

    @NotBlank(message = "Employee name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
