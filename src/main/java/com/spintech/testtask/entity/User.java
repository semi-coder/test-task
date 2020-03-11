package com.spintech.testtask.entity;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import static com.google.common.collect.Sets.newHashSet;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="Actors_to_Users",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="actorId")})
    private Set<Actor> favouriteActors;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name="Movies_to_Users",
            joinColumns={@JoinColumn(name="userId")},
            inverseJoinColumns={@JoinColumn(name="movie")})
    private Set<Movie> watchedMovies;

}