package org.rest.teamapi.dto.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTORequest {

    @NotNull(message = "Name cannot be null")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Birth date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;

    @NotNull(message = "Name cannot be null")
    private String city;
}
