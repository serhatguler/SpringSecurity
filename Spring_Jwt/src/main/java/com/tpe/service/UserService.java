package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.RoleType;
import com.tpe.dto.RegisterRequest;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(RegisterRequest registerRequest) {

        if (userRepository.existsByUserName(registerRequest.getUserName())){
            throw  new ConflictException("username is already used");
        }

        roleRepository.findByType(RoleType.ROLE_STUDENT)
                .orElseThrow(()-> new ResourceNotFoundException("role is not found"));
        Set<Role> roles = new HashSet<>();

        User user = new User(registerRequest.getFirstName(),
                registerRequest.getLastName(),registerRequest.getUserName(),
                passwordEncoder.encode(registerRequest.getPassword()),roles);
        userRepository.save(user);

    }
}
