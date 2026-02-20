package org.rest.teamapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.assemblers.PlayerAssembler;
import org.rest.teamapi.assemblers.TeamAssembler;
import org.rest.teamapi.dto.player.PlayerDTOResponse;
import org.rest.teamapi.dto.team.TeamDTORequest;
import org.rest.teamapi.dto.team.TeamDTOResponse;
import org.rest.teamapi.models.PlayerModel;
import org.rest.teamapi.models.TeamModel;
import org.rest.teamapi.repository.PlayerRepository;
import org.rest.teamapi.repository.SigningsRepository;
import org.rest.teamapi.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final SigningsRepository signingsRepository;
    private final PlayerAssembler playerAssembler;
    private final TeamAssembler teamAssembler;

    /**
     * Retrieves all teams from the database.
     *
     * @return List of TeamDTOs representing all teams.
     * */
    public List<TeamDTOResponse> getAllTeams() {

        log.info("Fetching all teams from database");
        return teamAssembler.toCollectionModel(teamRepository.findAll());
    }

    /**
     * Retrieves a team by its unique ID.
     *
     * @param id Unique identifier of the team.
     * @return TeamDTO representing the team with the specified ID.
     * */
    public TeamDTOResponse getTeamById(Integer id) {

        log.info("Searching for team with ID: {}", id);
        return teamRepository.findById(id)
                .map(teamAssembler::toModel)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + id));
    }

    /**
     * Retrieves the current roster of a team by its unique ID.
     *
     * @param teamId Unique identifier of the team.
     * @return List of PlayerDTOs representing the current roster of the team.
     * */
    public List<PlayerDTOResponse> getTeamRoster(Integer teamId) {

        log.info("Fetching active roster for team ID: {}", teamId);
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Team not found");
        }
        List<PlayerModel> players = signingsRepository.findActivePlayersByTeam(teamId, LocalDate.now());

        return playerAssembler.toCollectionModel(players);
    }

    /**
     * Creates a new team in the database.
     *
     * @param dto TeamModel object containing team details.
     * @return TeamDTO representing the newly created team.
     * */
    public TeamDTOResponse createTeam(TeamDTORequest dto) {

        if (teamRepository.existsByEmail(dto.getEmail())){
            throw  new RuntimeException("Creation failed: Email already in use: " + dto.getEmail());
        }

        TeamModel teamModel = new TeamModel();
        log.info("Saving new team: {}", dto.getName());
        mapRequestToModel(dto, teamModel);
        log.info("Team {} created successfully", dto.getName());

        return teamAssembler.toModel(teamRepository.save(teamModel));
    }

    /**
     * Updates an existing team.
     * @param id The ID of the team to update.
     * @param dto The new data for the team.
     * @return The updated team as a DTO.l
     */
    public TeamDTOResponse updateTeam(Integer id, TeamDTORequest dto) {

        log.info("Updating team with ID: {}", id);

        TeamModel existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Update failed: Team not found with ID: " + id));
        mapRequestToModel(dto, existingTeam);
        log.info("Team with ID {} updated successfully", id);

        return teamAssembler.toModel(teamRepository.save(existingTeam));
    }

    /**
     * Deletes a team by its unique ID.
     *
     * @param id Unique identifier of the team to be deleted.
     * */
    public void deleteTeam(Integer id) {

        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Team not found with ID: " + id);
        }
        teamRepository.deleteById(id);
        log.info("Team with ID {} deleted successfully", id);
    }

    private void mapRequestToModel(TeamDTORequest dto, TeamModel model) {

        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setCity(dto.getCity());
        model.setSince(dto.getSince());
    }
}
