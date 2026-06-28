package com.student.controllers;

import com.student.dto.AdminLoginRequestDto;
import com.student.dto.AuthResponse;
import com.student.dto.StudentLoginRequestDto;
import com.student.services.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
    @RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@Valid @RequestBody AdminLoginRequestDto loginRequestDto){
        AuthResponse authResponse = authenticationService.adminLogin(loginRequestDto);
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/student/login")
    public ResponseEntity<AuthResponse> studentLogin(@Valid @RequestBody StudentLoginRequestDto studentLoginRequestDto){

        AuthResponse authResponse = authenticationService.studentLogin(studentLoginRequestDto);
        return ResponseEntity.ok().body(authResponse);

    }


}
