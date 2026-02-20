package org.rest.teamapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Signings")
@Setter
@Getter
public class SigningsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSignings;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_player")
    private PlayerModel player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private TeamModel team;

    private LocalDate since;
    private LocalDate until;
}
