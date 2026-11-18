
package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.*;
import com.example.employeemanagement.entity.Department;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // CREATE DEPARTMENT
    public DepartmentResponseDTO createDepartment(DepartmentDTO dto) {

        if (departmentRepository.existsByDepartmentNameIgnoreCase(
                dto.getDepartmentName())) {

            throw new IllegalArgumentException(
                    "Department already exists: " + dto.getDepartmentName());
        }

        Department department = new Department();

        department.setDepartmentName(dto.getDepartmentName());

        return toDTO(departmentRepository.save(department));
    }

    // GET ALL DEPARTMENTS
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // GET DEPARTMENT BY ID
    @Transactional(readOnly = true)
    public DepartmentResponseDTO getDepartmentById(Long id) {

        return toDTO(findDepartment(id));
    }

    // UPDATE DEPARTMENT
    public DepartmentResponseDTO updateDepartment(Long id, DepartmentDTO dto) {

        Department department = findDepartment(id);

        department.setDepartmentName(dto.getDepartmentName());

        return toDTO(departmentRepository.save(department));
    }

    // DELETE DEPARTMENT
    @Transactional
    public void deleteDepartment(Long id) {

        Department department = findDepartment(id);

        if (!department.getEmployees().isEmpty()) {

            throw new IllegalArgumentException(
                    "Cannot delete a department that has employees. Reassign or delete them first.");
        }

        departmentRepository.delete(department);
    }

    // GET EMPLOYEES IN DEPARTMENT
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getDepartmentEmployees(Long id) {

        Department department = findDepartment(id);

        return department.getEmployees()
                .stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        department.getId(),
                        department.getDepartmentName()
                ))
                .toList();
    }

    // FIND DEPARTMENT
    private Department findDepartment(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id: " + id));
    }

    // CONVERT ENTITY TO DTO
    private DepartmentResponseDTO toDTO(Department department) {

        List<EmployeeResponseDTO> employees = department.getEmployees()
                .stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getName(),
                        employee.getEmail(),
                        employee.getSalary(),
                        department.getId(),
                        department.getDepartmentName()
                ))
                .toList();

        return new DepartmentResponseDTO(
                department.getId(),
                department.getDepartmentName(),
                employees
        );
    }
}
