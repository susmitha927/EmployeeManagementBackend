
package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findBySalaryGreaterThan(Double salary);

    List<Employee> findByNameContainingIgnoreCase(String keyword);

    List<Employee> findBySalaryBetween(Double min, Double max);

    // JPQL Query
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesWithSalaryAbove(
            @Param("salary") Double salary);

    // Native SQL Query
    @Query(value = "SELECT * FROM employees WHERE salary BETWEEN :min AND :max",
            nativeQuery = true)
    List<Employee> findEmployeesBySalaryRangeNative(
            @Param("min") Double min,
            @Param("max") Double max);

    Page<Employee> findAll(Pageable pageable);
}
