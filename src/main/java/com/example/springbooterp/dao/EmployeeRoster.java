package com.example.springbooterp.dao;

import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Table(name = "employeeRoster")
@Data
public class EmployeeRoster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] fileBytes;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
