package com.example.springbooterp.service;

import com.example.springbooterp.dao.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface EmployeeService {



    Employee applyPatchToEmployee(JsonPatch patch, Employee employee) throws JsonPatchException, JsonProcessingException;

    Page<Employee> getEmployeesWithPagination(int offset, int pageSize);


    public String encryptPassword(String password);

    public boolean CheckUserEmailExists(String email);

    public List<Employee> getEmployeeByEmail(String email);

    public boolean validateUserPassword(String inputPassword, String encryptedPassword);

    public List<Employee> getAllEmployee();

    public Employee registerNewAccount(Employee employee);
}
