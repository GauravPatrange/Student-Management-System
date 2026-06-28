package com.student.security;

import com.student.entity.Admin;
import com.student.entity.Student;
import com.student.repository.AdminRepository;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CustomUserDetailsService(AdminRepository adminRepository, StudentRepository studentRepository) {
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if(adminOptional.isPresent()){
            Admin admin =  adminOptional.get();
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority(admin.getRole())))
                    .build();
        }

        Optional<Student> byStudentCode = studentRepository.findByStudentCode(username);
        if(byStudentCode.isPresent()) {
            Student student = byStudentCode.get();
            return User.builder()
                    .username(student.getStudentCode())
                    .password("")
                    .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT")))
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username : "+username);
    }
}
