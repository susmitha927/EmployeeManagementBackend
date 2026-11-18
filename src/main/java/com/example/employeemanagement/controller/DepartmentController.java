
package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.service.DepartmentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // CREATE
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> create(
            @Valid @RequestBody DepartmentDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(dto));
    }

    // GET ALL
    @GetMapping
    public List<DepartmentResponseDTO> getAll() {

        return departmentService.getAllDepartments();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public DepartmentResponseDTO getById(@PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public DepartmentResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO dto) {

        return departmentService.updateDepartment(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }

    // GET EMPLOYEES IN DEPARTMENT
    @GetMapping("/{id}/employees")
    public List<EmployeeResponseDTO> employees(@PathVariable Long id) {

        return departmentService.getDepartmentEmployees(id);
    }
}
