package com.example.springbooterp.controller;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.repo.EmployeeRepo;
import com.example.springbooterp.security.TokenSecurity;
import com.example.springbooterp.service.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@OpenAPIDefinition(
        info = @Info(
                title = "Auth API for employee ERP.",
                description = "Employee login , JWT generation and validation",
                version = "v1"
        )

)
public class AuthServerController {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeService employeeService;


    @Autowired
    TokenSecurity tokenSecurity;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Record Added.",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "409", description = "Unable to Register",
                content =  @Content(
                        schema = @Schema(implementation = ResponseStatusException.class)
                ))
    })
    @Operation(summary = "Employee Register", description = "Return JWT Token")
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee){
        //TODO: Exception handling
        employee.setPassword(employeeService.encryptPassword(employee.getPassword()));
        return new ResponseEntity<>(employeeService.registerNewAccount(employee), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee Loged in .",
                    content = @Content(
                            schema = @Schema(implementation = Employee.class))),
            @ApiResponse(responseCode
                    = "409", description = "Unable to Login")
    })
    public ResponseEntity<String> employeeLogin(@RequestParam String email, @RequestParam String inputPassword) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        //TODO: Exception handling
        if (employeeService.CheckUserEmailExists(email)) {
            Employee employeeByEmail = employeeService.getEmployeeByEmail(email).get(0);
            //System.out.println(encryptedInputPassword + "/n Existing record pass: /n" + employeeByEmail.getPassword() + "existing record ewmail " + employeeByEmail.getEmail());
            if (employeeService.validateUserPassword(inputPassword,employeeByEmail.getPassword()))

                return new ResponseEntity<>(tokenSecurity.generateJwtToken(employeeByEmail), HttpStatus.OK);
            else
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials.");
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No user found by given email" );
    }

    @GetMapping("/validateToken/{jwtToken}")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200", description = "JWT token verified"),
            @ApiResponse(responseCode = "400", description = "Token verification failed")
    })
    public ResponseEntity<List<Employee>> validateToken(@PathVariable(required = false) String jwtToken){
        //TODO: Exception handling

//        if(jwtToken.equals("one"))
//            throw  new MethodArgumentNotValidException(ContentUtils.getContentErrorsFrom(get));

        String employee_email = tokenSecurity.validateJWT(jwtToken);

        if (employeeService.CheckUserEmailExists(employee_email)) {
            return new ResponseEntity<>(employeeService.getEmployeeByEmail(employee_email), HttpStatus.CREATED);
        }

            //return new ResponseEntity<>(HttpStatus.CONFLICT);
        return null;
    }
}

