package com.student.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
    @Email(message = "Invalid email! Please enter correct format")
    private String email;
    private String mobileNumber;
    private String parentsName;
    private List<AddressRequestDto> addresses;
}
