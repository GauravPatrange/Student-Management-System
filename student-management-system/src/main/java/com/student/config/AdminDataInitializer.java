package com.student.config;

import com.student.entity.Admin;
import com.student.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminDataInitializer {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminDataInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData(){
        return args -> {
            if(adminRepository.findByUsername("admin").isEmpty()){
                Admin admin = new Admin();
                admin.setUsername("admin");
                    admin.setPassword(passwordEncoder.encode("12345"));
                admin.setRole("ROLE_ADMIN");
                adminRepository.save(admin);
            }
        };
    }
}
