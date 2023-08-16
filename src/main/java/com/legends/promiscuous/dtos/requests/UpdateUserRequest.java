package com.legends.promiscuous.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String houseNumber;
    private String street;
    private String state;
    private String country;
    private String password;
    private String phoneNumber;
    private String gender;
    private Set<String> interests;
    private MultipartFile profileImage;
}
