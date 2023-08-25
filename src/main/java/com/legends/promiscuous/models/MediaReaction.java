package com.legends.promiscuous.models;

import com.legends.promiscuous.enums.Reaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.AUTO;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MediaReaction {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Enumerated(value = STRING)
    private Reaction reaction;
    private Long User;
}
