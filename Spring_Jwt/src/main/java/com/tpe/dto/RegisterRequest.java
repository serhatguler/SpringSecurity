package com.tpe.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data//getter setter ,allargs,noargs,tostring
public class RegisterRequest {

    @NotBlank(message = "provide first name")
    private String firstName;


    @NotBlank(message = "provide last name")
    private String lastName;


    @NotBlank(message = "provide username")
    @Size(min = 5,max = 10)
    private String userName;

    @NotBlank(message = "provide valid password")
    @Size(min = 4,max = 8)
    private String password;



}
