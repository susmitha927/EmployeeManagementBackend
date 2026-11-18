
package com.example.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Double salary;
    private Long departmentId;
    private String departmentName;
}
