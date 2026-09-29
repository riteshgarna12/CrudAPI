package com.example.springcrud.controller;

import com.example.springcrud.dto.EmployeeDto;
import com.example.springcrud.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // GET /api/employees — get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // GET /api/employees/{id} — get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // POST /api/employees — create new employee
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto created = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // PUT /api/employees/{id} — full update
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
                                                       @Valid @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDto));
    }

    // PATCH /api/employees/{id} — partial update
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> patchEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.patchEmployee(id, employeeDto));
    }

    // DELETE /api/employees/{id} — delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully with id: " + id);
    }
}
