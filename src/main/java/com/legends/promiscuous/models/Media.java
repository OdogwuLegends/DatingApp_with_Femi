package com.legends.promiscuous.models;

import com.legends.promiscuous.enums.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Media")
@Setter
@Getter
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
//    @Enumerated
//    @OneToMany
//    private List<Reaction> reactions;
    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<Reaction> reactions;

    @Column(columnDefinition = "MEDIUMTEXT",length = 1000, unique = true)
    private String url;

    @ManyToOne
    private User user;
//    private Integer reactionCount = BigInteger.ZERO.intValue();

}
