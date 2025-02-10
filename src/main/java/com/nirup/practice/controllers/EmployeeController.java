package com.nirup.practice.controllers;

import com.nirup.practice.dto.EmployeeDTO;
//import com.nirup.practice.entities.EmployeeEntity;
//import com.nirup.practice.repositories.EmployeeRepository;
import com.nirup.practice.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

//    private final EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getmessage")
    public String getMessage() {
        return "hello world";
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeByID(@PathVariable("employeeId") Long id) {
//        return new EmployeeDTO("nirup",id,21,"nirup@gmail.com",LocalDate.of(2024,02,03), true);
//        return employeeService.getEmployeeById(id);
        Optional<EmployeeDTO> employeeDTO=employeeService.getEmployeeById(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(()-> new NoSuchElementException("Employee Not Found"));
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleEmployeeNotFound(NoSuchElementException exception){
//        return new ResponseEntity<>("Employee not Found",HttpStatus.NOT_FOUND);
//    }


    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false, name = "inputAge") Integer age, @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        System.out.print(inputEmployee);
        EmployeeDTO savedEmployee=employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

//    @PostMapping
//    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
//        inputEmployee.setId(101);
//        return inputEmployee;
//    }


    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeByID(@PathVariable Long employeeId) {
        boolean gotDeleted= employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(gotDeleted);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates , @PathVariable Long employeeId){
        EmployeeDTO employeeDTO=employeeService.updatePartialEmployeeById(employeeId,updates);
        if(employeeDTO==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }

}
