package com.example.springbooterp.dao;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
public class EmployeeRosterInput {
    private Long Id;
    private MultipartFile rosterFile;
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @javax.persistence.Id
    public Long getId() {
        return id;
    }
}
