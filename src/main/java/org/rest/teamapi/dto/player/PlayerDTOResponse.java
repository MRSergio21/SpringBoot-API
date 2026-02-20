package org.rest.teamapi.dto.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTOResponse {

    private Integer idPlayer;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String position;
    private String gender;
    private Double weight;
    private Double high;
    private Double imc;
    private Double fat;
}
