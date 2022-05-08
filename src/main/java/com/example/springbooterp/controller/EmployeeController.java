package com.example.springbooterp.controller;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.repo.EmployeeRepo;
import com.example.springbooterp.service.EmployeeService;
import com.github.fge.jsonpatch.JsonPatch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@OpenAPIDefinition(
        info= @Info(
                title = "Employee ERP RESTful APIs",
                version = "v1",
                description = "CRUD operations on restul APIs"
        )
)
@RestController
@RequestMapping("/api/v1")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepo employeeRepo;



    @GetMapping(value = "/allEmployee", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success.",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode = "400", description = "No records found.")
    })
    @Operation(
            summary = "Get list of all employees",
            description = "Retrieves and returns the Employee List from the system "
                    + "service running on the SQL DB.")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        return new ResponseEntity<>(employeeService.getAllEmployee(),HttpStatus.OK);

    }


    @PostMapping(value = "/employees", consumes = { "application/json", "application/xml" })
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee Record Added.",
            content = @Content(
                    schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "400", description = "No records found")
    })
    public ResponseEntity<Employee> addEmployee( @Parameter(description="Employee to add.",
            required=true, schema=@Schema(implementation = Employee.class))
                                                     @Valid @RequestBody Employee employee){
        //TODO: Exception handling
        return new ResponseEntity<>(employeeRepo.save(employee),HttpStatus.CREATED);
    }

    //Reading Material - https://www.baeldung.com/spring-rest-json-patch
    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id){
        return employeeRepo.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    return employeeRepo.save(existingEmployee);
                })
                .orElseGet(() ->{
                    return employeeRepo.save(employee);
                });
    }


    @PatchMapping(value = "/employee/{id}", consumes = "application/json-pacth+json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee Record Updated.",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "400", description = "No records found")
    })
    @Operation(summary = "Update existing record", description = "If not found, creates a record")
    public ResponseEntity<Employee> patchEmployee ( @PathVariable Long id, @RequestBody JsonPatch patch){
        try{
            Employee employee = employeeRepo.findById(id).orElseThrow(Exception::new);
            return  new ResponseEntity<>(employeeService.applyPatchToEmployee(patch,employee), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/page/{offset}/{pageSize}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee records returned."),
            @ApiResponse(responseCode = "400", description = "No employee records found.")
    })
    public ResponseEntity<Page<Employee>> getEmployeesWithPagination(@PathVariable int offset, @PathVariable int pageSize){
        return new ResponseEntity<>(employeeService.getEmployeesWithPagination(offset,pageSize),HttpStatus.OK);
    }
}
