package org.rest.teamapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.assemblers.PlayerAssembler;
import org.rest.teamapi.dto.player.PlayerDTORequest;
import org.rest.teamapi.dto.player.PlayerDTOResponse;
import org.rest.teamapi.models.PlayerModel;
import org.rest.teamapi.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerAssembler playerAssembler;

    /**
     * Retrieves all players from the database.
     * @return List of PlayerDTOs representing all players.
     */
    public List<PlayerDTOResponse> getAllPlayers() {

        log.info("Fetching all players from the database");
        return playerAssembler.toCollectionModel(playerRepository.findAll());
    }

    /**
     * Retrieves a player by their unique ID.
     * @param id Unique identifier of the player.
     * @return PlayerDTO representing the player with the specified ID.
     * @throws RuntimeException if the player is not found.
     */
    public PlayerDTOResponse getPlayerById(Integer id) {

        log.info("Searching for player with ID: {}", id);
        PlayerModel player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found with ID: " + id));
        return playerAssembler.toModel(player);
    }

    /**
     * Creates a new player in the database.
     * @param dto PlayerModel object containing player details.
     * @return PlayerDTO representing the newly created player.
     */
    public PlayerDTOResponse createPlayer(PlayerDTORequest dto) {

        if (playerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use: " + dto.getEmail());
        }
        validateAgeRange(dto.getBirthDate());
        PlayerModel model = new PlayerModel();
        log.info("Creating new player: {} {}", dto.getFirstName(), dto.getLastName());
        mapRequestToModel(dto, model);
        log.info("Player {} {} created successfully", dto.getFirstName(), dto.getLastName());

        return playerAssembler.toModel(playerRepository.save(model));
    }

    /**
     * Updates an existing player's details.
     * @param id Unique identifier of the player to be updated.
     * @param dto PlayerDTO.Request containing updated player details.
     * @return PlayerDTO representing the updated player.
     * @throws RuntimeException if the player is not found.
     */
    public PlayerDTOResponse updatePlayer(Integer id, PlayerDTORequest dto) {

        validateAgeRange(dto.getBirthDate());
        log.info("Updating player with ID: {}", id);
        PlayerModel existingPlayer = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found with ID: " + id));
        mapRequestToModel(dto, existingPlayer);
        log.info("Player with ID {} updated successfully", id);

        return playerAssembler.toModel(playerRepository.save(existingPlayer));
    }

    /**
     * Deletes a player by their unique ID.
     * @param id Unique identifier of the player to be deleted.
     * @throws RuntimeException if the player ID does not exist.
     */
    public void deletePlayer(Integer id) {

        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Player ID does not exist: " + id);
        }
        playerRepository.deleteById(id);
        log.info("Team with ID {} deleted successfully", id);
    }

    /**
     * Maps the fields from a PlayerDTO.Request to a PlayerModel.
     * @param dto The PlayerDTO.Request containing player details.
     * @param model The PlayerModel to be populated.
     */
    private void mapRequestToModel(PlayerDTORequest dto, PlayerModel model) {

        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setEmail(dto.getEmail());
        model.setBirthDate(dto.getBirthDate());
        model.setPosition(dto.getPosition());
        model.setGender(dto.getGender());
        model.setWeight(dto.getWeight());
        model.setHigh(dto.getHigh());
        double h = dto.getHigh();
        double normalizedHeight = h > 3 ? h / 100 : h;
        model.setImc(Math.round((dto.getWeight() / Math.pow(normalizedHeight, 2)) * 100.0) / 100.0);
        model.setFat(dto.getFat());
    }

    private void validateAgeRange (LocalDate birthDate) {

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 16 || age > 50) {
            log.warn("Player age {} is out of valid range (16-50 years)", age);
            throw new RuntimeException("Player age must be between 16 and 50 years. Provided age: " + age);
        }
    }
}