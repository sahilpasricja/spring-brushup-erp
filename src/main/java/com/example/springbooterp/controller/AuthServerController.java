package com.example.springbooterp.controller;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.repo.EmployeeRepo;
import com.example.springbooterp.service.EmployeeService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.templateparser.markup.HTMLTemplateParser;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthServerController {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Record Added.",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "409", description = "Unable to Register")
    })
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee){
        //TODO: Exception handling
        if (!employeeService.CheckUserExists(employee.getEmail())) {
            employee.setPassword(employeeService.encryptPassword(employee.getPassword()));
            return new ResponseEntity<>(employeeRepo.save(employee), HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Record Added.",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "409", description = "Unable to Login")
    })
    public ResponseEntity<Employee> employeeLogin(@RequestParam String email, @RequestParam String inputPassword){
        //TODO: Exception handling
        if (employeeService.CheckUserExists(email)) {
            Employee employeeByEmail = employeeService.getEmployeeByEmail(email).get(0);
            //System.out.println(encryptedInputPassword + "/n Existing record pass: /n" + employeeByEmail.getPassword() + "existing record ewmail " + employeeByEmail.getEmail());
            if (employeeService.validateUserPassword(inputPassword,employeeByEmail.getPassword()))
                return new ResponseEntity<>(employeeByEmail, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

