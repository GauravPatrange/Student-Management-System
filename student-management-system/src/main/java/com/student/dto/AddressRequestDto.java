package com.student.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {
    private String addressType;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;

}
