package com.example.springbooterp.service;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.dao.EmployeeRoster;
import com.example.springbooterp.dao.EmployeeRosterInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;


public interface EmployeeService {

    List<Employee> getEmployees();

    Employee applyPatchToEmployee(JsonPatch patch, Employee employee) throws JsonPatchException, JsonProcessingException;

    Page<Employee> getEmployeesWithPagination(int offset, int pageSize);

    public EmployeeRoster employeeRosterInputToEmployeeRoster(EmployeeRosterInput employeeRosterInput) throws IOException;

    public String encryptPassword(String password);

    public boolean CheckUserExists(String email);

    public List<Employee> getEmployeeByEmail(String email);

    public boolean validateUserPassword(String inputPassword, String encryptedPassword);
}
