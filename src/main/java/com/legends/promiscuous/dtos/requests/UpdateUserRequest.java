package com.legends.promiscuous.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
