package org.rest.teamapi.dto.signings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigningsDTOResponse {

    private Integer idSignings;
    private String playerName;
    private String teamName;
    private LocalDate since;
    private LocalDate until;
}
