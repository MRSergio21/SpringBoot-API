package org.apirest.talky.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class PostResponse {

    private String content;
    private String img;
    private LocalDateTime dateCreation;

}
