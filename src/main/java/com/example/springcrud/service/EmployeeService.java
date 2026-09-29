package com.example.springcrud.service;

import com.example.springcrud.dto.EmployeeDto;
import com.example.springcrud.entity.Employee;
import com.example.springcrud.exception.ResourceNotFoundException;
import com.example.springcrud.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // -------------------- GET ALL --------------------
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -------------------- GET BY ID --------------------
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return toDto(employee);
    }

    // -------------------- CREATE --------------------
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = toEntity(dto);
        Employee saved = employeeRepository.save(employee);
        return toDto(saved);
    }

    // -------------------- UPDATE (PUT - full replace) --------------------
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setDepartment(dto.getDepartment());
        existing.setSalary(dto.getSalary());

        Employee updated = employeeRepository.save(existing);
        return toDto(updated);
    }

    // -------------------- PATCH (partial update) --------------------
    public EmployeeDto patchEmployee(Long id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getDepartment() != null) existing.setDepartment(dto.getDepartment());
        if (dto.getSalary() != null) existing.setSalary(dto.getSalary());

        Employee updated = employeeRepository.save(existing);
        return toDto(updated);
    }

    // -------------------- DELETE --------------------
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existing);
    }

    // -------------------- MAPPING HELPERS --------------------
    private EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .build();
    }

    private Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .salary(dto.getSalary())
                .build();
    }
}
