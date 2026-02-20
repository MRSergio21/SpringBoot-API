package org.rest.teamapi.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.dto.player.PlayerDTORequest;
import org.rest.teamapi.dto.player.PlayerDTOResponse;
import org.rest.teamapi.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/championsleague/player")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Player Controller", description="Endpoints for managing players with CRUD operations")

public class PlayerController {

    private final PlayerService playerService;

    /**
     * Retrieves all players.
     *
     * @return List of PlayerDTO.Response containing all players.
     * */
    @GetMapping
    public List<PlayerDTOResponse> getAllPlayers() {

        log.info("Fetching all players");
        return playerService.getAllPlayers();
    }

    /**
     * Retrieves a player by their unique ID.
     *
     * @param id Unique identifier of the player.
     * @return ResponseEntity containing the PlayerDTO if found, otherwise a 404 status.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTOResponse> getPlayerById(@PathVariable Integer id) {

        log.info("Fetching player with ID: {}", id);
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    /**
     * Creates a new player.
     *
     * @param request PlayerDTO.Request containing player details.
     * @return ResponseEntity containing the created PlayerDTO.
     * */
    @PostMapping
    public ResponseEntity<PlayerDTOResponse> createPlayer(@Valid @RequestBody PlayerDTORequest request) {

        log.info("Creating new player: {} {}", request.getFirstName(), request.getLastName());
        return ResponseEntity.status(201).body(playerService.createPlayer(request));
    }

    /**
     * Updates an existing player by their unique ID.
     *
     * @param id Unique identifier of the player to be updated.
     * @param request PlayerDTO.Request containing updated player details.
     * @return ResponseEntity containing the updated PlayerDTO.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTOResponse> updatePlayer(@PathVariable Integer id, @RequestBody PlayerDTORequest request) {

        log.info("Updating player with ID: {}", id);
        return ResponseEntity.ok(playerService.updatePlayer(id, request));
    }

    /**
     * Deletes a player by their unique ID.
     *
     * @param id Unique identifier of the player to be deleted.
     * @return ResponseEntity with no content status.
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {

        log.info("Deleting player with ID: {}", id);
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

}
