package com.example.springbooterp.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Data

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Schema(hidden = true)
    private long id;


    private String firstName;

    private String lastName;

    private String email;

    @Schema(hidden = true)
    private String password;

}
