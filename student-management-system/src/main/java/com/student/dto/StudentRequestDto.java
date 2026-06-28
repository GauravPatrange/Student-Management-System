package com.student.dto;

import com.student.entity.Address;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {

    @NotBlank(message = "Student name is required")
    private String name;
    @NotNull(message = "Date of brith is required")
    private LocalDate dateOfBrith;
    @NotBlank(message = "gender is required")
    private String gender;
    @NotBlank(message = "Student code is reqired")
    private String studentCode;
    private List<AddressRequestDto> addresses;

}
