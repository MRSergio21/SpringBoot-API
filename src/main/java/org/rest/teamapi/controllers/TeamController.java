package org.rest.teamapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.dto.player.PlayerDTOResponse;
import org.rest.teamapi.dto.team.TeamDTORequest;
import org.rest.teamapi.dto.team.TeamDTOResponse;
import org.rest.teamapi.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/championsleague/team")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Team Controller", description = "Endpoints for managing teams with CRUD operations")

public class TeamController {

    private final TeamService teamService;

    /**
     * Retrieves all teams.
     *
     * @return ResponseEntity containing a list of TeamDTOs.
     * */
    @GetMapping
    public List<TeamDTOResponse> getAllTeams() {

        log.info("Fetching all teams");
        return teamService.getAllTeams();
    }

    /**
     * Retrieves a team by its unique ID.
     *
     * @param id Unique identifier of the team.
     * @return ResponseEntity containing the TeamDTO if found, otherwise a 404 status.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTOResponse> getTeamById(@PathVariable Integer id) {

        log.info("Fetching team with ID: {}", id);
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    /**
     * Retrieves the current roster of a team by its unique ID.
     *
     * @param id Unique identifier of the team.
     * @return ResponseEntity containing a list of PlayerDTOs representing the current roster.
     * */
    @GetMapping("/{id}/roster")
    @Operation(summary = "Ver plantilla actual", description = "Lista todos los jugadores con contrato vigente en este equipo.")
    public ResponseEntity<List<PlayerDTOResponse>> getRoster(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeamRoster(id));
    }

    /**
     * Creates a new team.
     *
     * @param request TeamModel entity containing team details.
     * @return ResponseEntity containing the created TeamDTO.
     * */
    @PostMapping
    public ResponseEntity<TeamDTOResponse> createTeam (@Valid @RequestBody TeamDTORequest request) {

        log.info("Creating new team: {}", request.getName());
        return ResponseEntity.status(201).body(teamService.createTeam(request));
    }

    /**
     * Updates an existing team by its unique ID.
     *
     * @param id   Unique identifier of the team to be updated.
     * @param request TeamModel entity containing updated team details.
     * @return ResponseEntity containing the updated TeamDTO.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<TeamDTOResponse> updateTeam(@PathVariable Integer id, @RequestBody TeamDTORequest request) {

        log.info("Updating team with ID: {}", id);
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    /**
     * Deletes a team by its unique ID.
     *
     * @param id Unique identifier of the team to be deleted.
     * @return ResponseEntity with no content status.
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {

        log.info("Deleting team with ID: {}", id);
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}
