package com.student;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title="Student Management System REST API Documentation",
				description = "Student Management System Project",
				version="v1",
				contact = @Contact(
						name = "Gaurav Patrange",
						email = "gauravpatrange286@gmail.com"
				),
				license = @License(
						name = "Github",
						url = ""
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "REST Api Documentation"
		)
)
public class StudentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}

}
