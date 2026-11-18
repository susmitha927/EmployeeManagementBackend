
package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.*;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    // CREATE EMPLOYEE
    public EmployeeResponseDTO createEmployee(EmployeeDTO dto) {

        Department department = getDepartment(dto.getDepartmentId());

        Employee employee = new Employee();

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return toDTO(employeeRepository.save(employee));
    }

    // GET ALL EMPLOYEES
    public List<EmployeeResponseDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // GET EMPLOYEE BY ID
    public EmployeeResponseDTO getEmployeeById(Long id) {

        return toDTO(getEmployee(id));
    }

    // UPDATE EMPLOYEE
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeDTO dto) {

        Employee employee = getEmployee(id);

        Department department = getDepartment(dto.getDepartmentId());

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return toDTO(employeeRepository.save(employee));
    }

    // DELETE EMPLOYEE
    public void deleteEmployee(Long id) {

        employeeRepository.delete(getEmployee(id));
    }

    // SEARCH BY DEPARTMENT
    public List<EmployeeResponseDTO> getEmployeesByDepartment(Long departmentId) {

        getDepartment(departmentId);

        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // SEARCH BY NAME
    public List<EmployeeResponseDTO> searchByName(String keyword) {

        return employeeRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // SALARY GREATER THAN
    public List<EmployeeResponseDTO> salaryGreaterThan(Double amount) {

        return employeeRepository.findBySalaryGreaterThan(amount)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // SALARY BETWEEN
    public List<EmployeeResponseDTO> salaryBetween(Double min, Double max) {

        checkRange(min, max);

        return employeeRepository.findBySalaryBetween(min, max)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // JPQL QUERY
    public List<EmployeeResponseDTO> jpqlSearch(Double salary) {

        return employeeRepository.findEmployeesWithSalaryAbove(salary)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // NATIVE SQL QUERY
    public List<EmployeeResponseDTO> nativeSearch(Double min, Double max) {

        checkRange(min, max);

        return employeeRepository.findEmployeesBySalaryRangeNative(min, max)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // PAGINATION AND SORTING
    public Page<EmployeeResponseDTO> getPage(
            int page, int size, String sortBy, String direction) {

        if (page < 0 || size < 1) {
            throw new IllegalArgumentException(
                    "Page must be 0 or greater and size must be positive.");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(this::toDTO);
    }

    // FIND EMPLOYEE
    private Employee getEmployee(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));
    }

    // FIND DEPARTMENT
    private Department getDepartment(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));
    }

    // CONVERT ENTITY TO DTO
    private EmployeeResponseDTO toDTO(Employee employee) {

        Department department = employee.getDepartment();

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getSalary(),
                department == null ? null : department.getId(),
                department == null ? null : department.getDepartmentName()
        );
    }

    // VALIDATE SALARY RANGE
    private void checkRange(Double min, Double max) {

        if (min > max) {
            throw new IllegalArgumentException(
                    "Minimum salary cannot be greater than maximum salary.");
        }
    }
}
