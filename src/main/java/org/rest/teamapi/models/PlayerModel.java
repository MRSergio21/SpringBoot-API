package org.rest.teamapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Player")

public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlayer;

    private String firstName;
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private LocalDate birthDate;
    private String position;
    private String gender;
    private Double weight;
    private Double high;
    private Double imc;
    private Double fat;
}
