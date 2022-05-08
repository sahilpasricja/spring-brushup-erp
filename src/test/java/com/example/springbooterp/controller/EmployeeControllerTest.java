package com.example.springbooterp.controller;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.repo.EmployeeRepo;
import com.example.springbooterp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;


    Employee employee;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    EmployeeService employeeService;

    final String FNAME = "Luxman";
    final String LNAME = "Chand";
    final String EMAIL = "Luxman@ayodhya.com";
    final String PASSWORD = "LsitaLoveKush";

    @BeforeEach
    void setUp(){
        employee = employeeRepo.save(Employee.builder()
                        .id(123)
                        .firstName(FNAME)
                        .lastName(LNAME)
                        .email(EMAIL)
                        .password(PASSWORD)
                        .build()
                );
    }

    @Test
    void getEmployees() throws Exception{
        System.out.println(mockMvc.perform(get("http://localhost:8080/api/v1/allEmployee")));

        mockMvc.perform(get("http://localhost:8080/api/v1/allEmployee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(FNAME)));

    }

    @Test
    void addEmpoyee() throws Exception{
        var objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(employee);
        mockMvc.perform(post("http://localhost:8080/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(requestJson))
                .andExpect(status().isCreated());

    }

    @Test
    void addEmployee() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(employee);
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated());
    }


}