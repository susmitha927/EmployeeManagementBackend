
package com.example.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {

    @NotBlank(message = "Department name is required")
    private String departmentName;
}
