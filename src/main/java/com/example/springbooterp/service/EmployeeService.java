package com.example.springbooterp.service;

import com.example.springbooterp.dao.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface EmployeeService {

    List<Employee> getEmployees();

    Employee applyPatchToEmployee(JsonPatch patch, Employee employee) throws JsonPatchException, JsonProcessingException;

    Page<Employee> getEmployeesWithPagination(int offset, int pageSize);
}
