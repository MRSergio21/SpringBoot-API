package org.rest.teamapi.assemblers;

import org.rest.teamapi.dto.team.TeamDTOResponse;
import org.rest.teamapi.models.TeamModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamAssembler {

    /** Convert Entity to DTO
     *
     * @param entity
     * @return TeamDTO
     */
    public TeamDTOResponse toModel (TeamModel entity){

        if(entity == null) return null;

        TeamDTOResponse dtoTeam = new TeamDTOResponse();
        dtoTeam.setIdTeam(entity.getIdTeam());
        dtoTeam.setName(entity.getName());
        dtoTeam.setEmail(entity.getEmail());
        dtoTeam.setSince(entity.getSince());
        dtoTeam.setCity(entity.getCity());
        return dtoTeam;
    }

    /** Convert DTO to Entity
     *
     * @param entities
     * @return List<TeamDTO>
     */
    public List<TeamDTOResponse> toCollectionModel (List<TeamModel> entities){
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
