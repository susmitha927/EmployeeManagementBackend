
package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.service.EmployeeService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // CREATE
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(
            @Valid @RequestBody EmployeeDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(dto));
    }

    // GET ALL
    @GetMapping
    public List<EmployeeResponseDTO> getAll() {

        return employeeService.getAllEmployees();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public EmployeeResponseDTO getById(@PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public EmployeeResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO dto) {

        return employeeService.updateEmployee(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    // SEARCH BY DEPARTMENT
    @GetMapping("/department/{departmentId}")
    public List<EmployeeResponseDTO> byDepartment(
            @PathVariable Long departmentId) {

        return employeeService.getEmployeesByDepartment(departmentId);
    }

    // SEARCH BY NAME
    @GetMapping("/search/name")
    public List<EmployeeResponseDTO> byName(
            @RequestParam String keyword) {

        return employeeService.searchByName(keyword);
    }

    // SALARY GREATER THAN
    @GetMapping("/search/salary-greater-than")
    public List<EmployeeResponseDTO> salaryGreater(
            @RequestParam Double amount) {

        return employeeService.salaryGreaterThan(amount);
    }

    // SALARY BETWEEN
    @GetMapping("/search/salary-between")
    public List<EmployeeResponseDTO> salaryBetween(
            @RequestParam Double min,
            @RequestParam Double max) {

        return employeeService.salaryBetween(min, max);
    }

    // JPQL
    @GetMapping("/search/jpql")
    public List<EmployeeResponseDTO> jpql(
            @RequestParam Double salary) {

        return employeeService.jpqlSearch(salary);
    }

    // NATIVE SQL
    @GetMapping("/search/native")
    public List<EmployeeResponseDTO> nativeSql(
            @RequestParam Double min,
            @RequestParam Double max) {

        return employeeService.nativeSearch(min, max);
    }

    // PAGINATION AND SORTING
    @GetMapping("/page")
    public Page<EmployeeResponseDTO> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return employeeService.getPage(page, size, sortBy, direction);
    }
}
