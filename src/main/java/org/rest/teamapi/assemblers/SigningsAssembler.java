package org.rest.teamapi.assemblers;

import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.dto.signings.SigningsDTOResponse;
import org.rest.teamapi.models.SigningsModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SigningsAssembler {

    public SigningsDTOResponse toModel (SigningsModel entity) {

        if(entity == null) return null;

        SigningsDTOResponse dto = new SigningsDTOResponse();
        dto.setIdSignings(entity.getIdSignings());
        dto.setSince(entity.getSince());
        dto.setUntil(entity.getUntil());

        if (entity.getPlayer() != null) {
            dto.setPlayerName(entity.getPlayer().getFirstName() + " " + entity.getPlayer().getLastName());
        } else {
            dto.setPlayerName("Without Player Assigned");
            log.warn("Signing ID {} has no Player assigned", entity.getIdSignings());
        }

        if (entity.getTeam() != null) {
            dto.setTeamName(entity.getTeam().getName());
        } else {
            dto.setTeamName("Without Team Assigned");
            log.warn("Signing ID {} has no Team assigned", entity.getIdSignings());
        }

        return dto;
    }

    public List<SigningsDTOResponse> toCollectionModel (List<SigningsModel> entities) {

        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
