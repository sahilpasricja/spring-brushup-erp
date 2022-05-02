package com.example.springbooterp.service;

import com.example.springbooterp.dao.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;


public interface EmployeeService {

    List<Employee> getEmployees();

    Employee applyPatchToEmployee(JsonPatch patch, Employee employee) throws JsonPatchException, JsonProcessingException;
}
