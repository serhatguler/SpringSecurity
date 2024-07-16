package com.tpe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank(message = "provide username")
    @Size(min = 5,max = 10)
    private String userName;

    @NotBlank(message = "provide valid password")
    @Size(min = 4,max = 8)
    private String password;

}
