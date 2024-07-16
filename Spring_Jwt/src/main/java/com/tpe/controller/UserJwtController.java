package com.tpe.controller;

import com.tpe.dto.LoginRequest;
import com.tpe.dto.RegisterRequest;
import com.tpe.security.JWTUtils;
import com.tpe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserJwtController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    //user register
    @PostMapping("/register")
    public ResponseEntity<String > registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);

    }

    //user login-->username,password-- respomse: TOKEN
    @PostMapping("/login")//standart
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.//username,passwordu valide eder eger yoksa exception firlatir
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword()));

        String token = jwtUtils.generateToken(authentication);


        return new ResponseEntity<>(token,HttpStatus.CREATED);

    }

    @GetMapping("/goodbye")
    @PreAuthorize("hasRole('STUDENT ')")
    public ResponseEntity<String> goodbye(){
        return ResponseEntity.ok("Goodbye Spring Securiy");

    }


}
