package org.rest.teamapi.dto.player;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTORequest {

    @NotNull(message = "First name cannot be null")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Birth date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String position;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @NotNull(message = "Weight cannot be null")
    private Double weight;

    @NotNull(message = "Height cannot be null")
    private Double high;

    private Double fat;
}
