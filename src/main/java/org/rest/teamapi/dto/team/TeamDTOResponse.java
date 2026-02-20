package org.rest.teamapi.dto.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTOResponse {

    private Integer idTeam;
    private String name;
    private String email;
    private LocalDate since;
    private String city;
}
