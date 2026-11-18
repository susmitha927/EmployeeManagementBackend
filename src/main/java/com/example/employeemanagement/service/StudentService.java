package com.example.employeemanagement.service;

import com.example.employeemanagement.entity.Student;
import com.example.employeemanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student existing = getStudent(id);

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setCourse(student.getCourse());

        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        repository.delete(getStudent(id));
    }
}