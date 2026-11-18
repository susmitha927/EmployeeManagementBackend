
package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class DepartmentResponseDTO {

    private Long id;
    private String departmentName;
    private List<EmployeeResponseDTO> employees;
}
