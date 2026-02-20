package org.rest.teamapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.assemblers.SigningsAssembler;
import org.rest.teamapi.dto.signings.SigningsDTORequest;
import org.rest.teamapi.dto.signings.SigningsDTOResponse;
import org.rest.teamapi.models.PlayerModel;
import org.rest.teamapi.models.SigningsModel;
import org.rest.teamapi.models.TeamModel;
import org.rest.teamapi.repository.PlayerRepository;
import org.rest.teamapi.repository.SigningsRepository;
import org.rest.teamapi.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SigningsService {

    private final SigningsRepository signingsRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final SigningsAssembler signingsAssembler;

    /**
     * Retrieves all signings from the repository and converts them to DTOs.
     * @return A list of SigningsDTO representing all signings.
     */
    public List<SigningsDTOResponse> getAllSignings() {

        log.info("Fetching all signings from the database");
        return signingsAssembler.toCollectionModel(signingsRepository.findAll());
    }

    /**
     * Retrieves a signing by its ID and converts it to a DTO.
     * @param id The ID of the signing to retrieve.
     * @return A SigningsDTO representing the signing.
     * @throws RuntimeException if the signing is not found.
     */
    public SigningsDTOResponse getSigningById(Integer id) {

        SigningsModel signin = signingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Signing not found"));
        return signingsAssembler.toModel(signin);
    }

    /**
     * Creates a new signing in the repository and converts it to a DTO.
     * @param dto The SigningsDTO containing signing details.
     * @return A SigningsDTO representing the created signing.
     */
    public SigningsDTOResponse createSigning(SigningsDTORequest dto) {

        isSigningValid(dto.getSince(), dto.getUntil());

        PlayerModel player = playerRepository.findById(dto.getIdPlayer())
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + dto.getIdPlayer()));

        int ageAtSigning = Period.between(player.getBirthDate(), dto.getSince()).getYears();
        if (ageAtSigning < 16 || ageAtSigning > 50) {
            throw new RuntimeException("Player is out of professional age range: " + ageAtSigning);
        }

        Optional<LocalDate> busyUntil = signingsRepository.findLatestContractEndDate(dto.getIdPlayer(), LocalDate.now());

        if (busyUntil.isPresent()) {
            throw new RuntimeException("The player is already committed. He will be a free agent on: " + busyUntil.get());
        }

        log.info("Creating signing for player: {} {}", player.getFirstName(), player.getLastName());
        SigningsModel model = new SigningsModel();
        mapRequestToModel(dto, model);

        return signingsAssembler.toModel(signingsRepository.save(model));
    }

    /**
     * Updates an existing signing in the repository and converts it to a DTO.
     * @param id The ID of the signing to update.
     * @param dto The SigningsDTO containing updated signing details.
     * @return A SigningsDTO representing the updated signing.
     * @throws RuntimeException if the signing is not found.
     */
    public SigningsDTOResponse updateSigning(Integer id, SigningsDTORequest dto) {

        log.info("Updating signing with ID: {}", id);
        isSigningValid(dto.getSince(), dto.getUntil());
        SigningsModel existing = signingsRepository.findById(id).orElseThrow(() -> new RuntimeException("Signing not found"));
        mapRequestToModel(dto, existing);
        log.info("Signing with ID: {} updated successfully", id);

        return signingsAssembler.toModel(signingsRepository.save(existing));
    }

    /**
     * Deletes a signing from the repository by its ID.
     * @param id The ID of the signing to delete.
     * @throws RuntimeException if the signing is not found.
     */
    public void deleteSigning(Integer id) {

        SigningsModel signing = signingsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signing not found"));

        if (signing.getUntil().isAfter(LocalDate.now())) {
            throw new RuntimeException("Cannot delete an active signing " + signing.getUntil());
        }

        signingsRepository.deleteById(id);
        log.info("Signing with ID: {} deleted successfully", id);
    }

    /**
     * Maps the fields from a SigningsDTO.Request to a SigningsModel.
     * @param dto The SigningsDTO.Request containing signing details.
     * @param model The SigningsModel to map the details to.
     */
    private void mapRequestToModel(SigningsDTORequest dto, SigningsModel model) {

        model.setSince(dto.getSince());
        model.setUntil(dto.getUntil());

        PlayerModel player = playerRepository.findById(dto.getIdPlayer())
                .orElseThrow(() -> new RuntimeException("Error: Player with ID " + dto.getIdPlayer() + " not found"));

        TeamModel team = teamRepository.findById(dto.getIdTeam())
                .orElseThrow(() -> new RuntimeException("Error: Team with ID " + dto.getIdTeam() + " not found"));

        model.setPlayer(player);
        model.setTeam(team);

        log.info("Successfully mapped Player {} and Team {} to Signing", player.getIdPlayer(), team.getIdTeam());
    }

    /**
     * Validates the signing dates.
     * @param since The start date of the signing.
     * @param until The end date of the signing.
     * @throws RuntimeException if the dates are invalid.
     */
    public void isSigningValid(LocalDate since, LocalDate until) {
        if (since == null || until == null) throw new RuntimeException("Dates mandatory");

        if (since.isAfter(until)) {
            throw new RuntimeException("Chronological error: 'Since' date must be before 'Until' date.");
        }

        long duration = java.time.temporal.ChronoUnit.YEARS.between(since, until);
        if (duration > 6) {
            throw new RuntimeException("Contract rejected: Maximum duration is 6 years. You tried: " + duration);
        }
    }
}
