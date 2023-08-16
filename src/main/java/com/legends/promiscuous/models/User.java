package com.legends.promiscuous.models;

import com.legends.promiscuous.enums.Gender;
import com.legends.promiscuous.enums.Interest;
import com.legends.promiscuous.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "users_table")
@Setter
@Getter
//@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ElementCollection()
    private Set<Interest> interests;

    private boolean isActive;
    private String createdAt;


    @PrePersist
    public void setCreatedAt(){
        var currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        createdAt = currentTime.format(formatter);
    }

}
