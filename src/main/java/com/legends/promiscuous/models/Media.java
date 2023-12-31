package com.legends.promiscuous.models;

import com.legends.promiscuous.enums.Reaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MediaReaction> reactions;

    @Column(unique = true)
    private String url;

    @ManyToOne
    private User user;
//    private Integer reactionCount = BigInteger.ZERO.intValue();


    //We added these ones below
    private boolean isLike;
    public Media() {
        this.reactions = new ArrayList<>();
    }
}
