package com.student.services.impl;

import com.student.dto.AdminLoginRequestDto;
import com.student.dto.AuthResponse;
import com.student.dto.StudentLoginRequestDto;
import com.student.entity.Student;
import com.student.exception.ResourseNotFoundException;
import com.student.exception.UserAuthenticationException;
import com.student.repository.StudentRepository;
import com.student.security.JWTUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthenticationServiceImpl {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final StudentRepository studentRepository;


    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JWTUtils jwtUtils, StudentRepository studentRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.studentRepository = studentRepository;
    }

    public AuthResponse adminLogin(AdminLoginRequestDto loginRequestDto){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            return AuthResponse.builder()
                    .token(token)
                    .type("Bearer")
                    .role("ADMIN")
                    .username(userDetails.getUsername())
                    .build();

        }catch (Exception exception){
            throw new UserAuthenticationException("Invalid admin credentails");
        }


    }

    public AuthResponse studentLogin(@Valid StudentLoginRequestDto studentLoginRequestDto) {
        Student student = studentRepository.findByStudentCodeAndDateOfBirth(studentLoginRequestDto.getStudentCode(), studentLoginRequestDto.getDateOfBirth())
                .orElseThrow(() -> new UserAuthenticationException(" Invalid student code or date of birth"));

        UserDetails userDetails = User.builder()
                .username(student.getStudentCode())
                .password("")
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT")))
                .build();

        String token = jwtUtils.generateToken(userDetails);
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .role("STUDENT")
                .username(student.getStudentCode())
                .build();
    }
}
