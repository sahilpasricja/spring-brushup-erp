package com.example.springbooterp.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Violation {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;

}
