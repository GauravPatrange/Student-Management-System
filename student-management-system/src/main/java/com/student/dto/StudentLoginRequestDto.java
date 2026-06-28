package com.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginRequestDto {

    @NotBlank(message = "Student code is required")
    private String studentCode;
    @NotNull(message = "Date of dirth is required")
    private LocalDate dateOfBirth;

}
