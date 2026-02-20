package org.rest.teamapi.assemblers;

import org.rest.teamapi.dto.player.PlayerDTOResponse;
import org.rest.teamapi.models.PlayerModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerAssembler {

    /** Convert Entity to DTO
     *
     * @param entity
     * @return PlayerDTO
     */
    public PlayerDTOResponse toModel (PlayerModel entity){

        if(entity == null) return null;

        PlayerDTOResponse dtoPlayer = new PlayerDTOResponse();
        dtoPlayer.setIdPlayer(entity.getIdPlayer());
        dtoPlayer.setFirstName(entity.getFirstName());
        dtoPlayer.setLastName(entity.getLastName());
        dtoPlayer.setEmail(entity.getEmail());
        dtoPlayer.setBirthDate(entity.getBirthDate());
        dtoPlayer.setPosition(entity.getPosition());
        dtoPlayer.setGender(entity.getGender());
        dtoPlayer.setWeight(entity.getWeight());
        dtoPlayer.setHigh(entity.getHigh());
        dtoPlayer.setImc(entity.getImc());
        dtoPlayer.setFat(entity.getFat());

        return dtoPlayer;
    }


    /** Convert DTO to Entity
     *
     * @param entities
     * @return List<PlayerDTO>
     */
    public List<PlayerDTOResponse> toCollectionModel (List<PlayerModel> entities){

        return entities.stream().map(this::toModel).collect(Collectors.toList());
    }
}
