package com.example.springbooterp.service;

import com.example.springbooterp.dao.Employee;
import com.example.springbooterp.dao.EmployeeRoster;
import com.example.springbooterp.dao.EmployeeRosterInput;
import com.example.springbooterp.repo.EmployeeRepo;
import com.example.springbooterp.repo.EmployeeRosterRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepo employeeRepo;

    PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();



    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee applyPatchToEmployee(JsonPatch patch, Employee targetEmployee) throws JsonPatchException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode patched = patch.apply(mapper.convertValue(targetEmployee,JsonNode.class));
        return mapper.treeToValue(patched, Employee.class);
    }

    @Override
    public Page<Employee> getEmployeesWithPagination(int offset, int pageSize) {
        return employeeRepo.findAll(PageRequest.of(offset,pageSize));
    }

//    @PostConstruct
//    public void initDB(){
//        List<Employee> employees = IntStream.range(1,100)
//                .mapToObj(i-> new Employee(i+20,"name" +i, "lname"+i, "email"+i))
//                .collect(Collectors.toList());
//        employeeRepo.saveAll(employees);
//    }
    public EmployeeRoster employeeRosterInputToEmployeeRoster(EmployeeRosterInput employeeRosterInput) throws IOException {
        EmployeeRoster employeeRoster = new EmployeeRoster();
        employeeRoster.setFileBytes(employeeRosterInput.getRosterFile().getBytes());
        employeeRoster.setId(employeeRosterInput.getId());

        return employeeRoster;
    }

    public String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean CheckUserExists(String email){
        List<Employee> employeesWithSameEmail = employeeRepo.findByEmail(email);
        if (employeesWithSameEmail.isEmpty())
            return false;
        else
            return true;
    }

    public boolean validateUserPassword(String inputPassword, String encryptedPassword){

        if (passwordEncoder.matches(inputPassword,encryptedPassword))
            return true;
        else
            return false;
    }


    public List<Employee> getEmployeeByEmail(String email){
        return employeeRepo.findByEmail(email);
    }

}

