package org.rest.teamapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Table(name = "Team")

public class TeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTeam;
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String city;
    private LocalDate since;

}
