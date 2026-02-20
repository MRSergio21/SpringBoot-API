package org.rest.teamapi.dto.signings;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SigningsDTORequest {

    private Integer idPlayer;
    private Integer idTeam;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate until;

    @AssertTrue(message = "The 'since' date must be before the 'until' date.")
    public boolean isValidDates() {
        if (since == null || until == null) return false;
        return since.isBefore(until);
    }
}
