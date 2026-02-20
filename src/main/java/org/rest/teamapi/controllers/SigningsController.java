package org.rest.teamapi.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.dto.signings.SigningsDTORequest;
import org.rest.teamapi.dto.signings.SigningsDTOResponse;
import org.rest.teamapi.services.SigningsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/signings")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Signings Controller", description = "Endpoints for managing player-team contracts with CRUD operations.")

public class SigningsController {

    private final SigningsService signingsService;

    /**
     * Retrieves all signings.
     * @return A ResponseEntity containing a list of SigningsDTO.
     */
    @GetMapping
    public List<SigningsDTOResponse> getAll() {

        log.info("Fetching all signings");
        return signingsService.getAllSignings();
    }

    /**
     * Retrieves a signing by its ID.
     * @param id The ID of the signing to retrieve.
     * @return A ResponseEntity containing the SigningsDTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SigningsDTOResponse> getById(@PathVariable Integer id) {

        log.info("Fetching signing with ID: {}", id);
        return ResponseEntity.ok(signingsService.getSigningById(id));
    }

    /**
     * Creates a new signing.
     * @param request The SigningsDTO containing signing details.
     * @return A ResponseEntity containing the created SigningsDTO.
     */
    @PostMapping
    public ResponseEntity<SigningsDTOResponse> create(@Valid @RequestBody SigningsDTORequest request) {

        return ResponseEntity.status(201).body(signingsService.createSigning(request));
    }

    /**
     * Updates an existing signing by its ID.
     * @param id The ID of the signing to update.
     * @param request The SigningsDTO containing updated signing details.
     * @return A ResponseEntity containing the updated SigningsDTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SigningsDTOResponse> update(@PathVariable Integer id, @RequestBody SigningsDTORequest request) {

        log.info("Updating signing with ID: {}", id);
        return ResponseEntity.ok(signingsService.updateSigning(id, request));
    }

    /**
     * Deletes a signing by its ID.
     * @param id The ID of the signing to delete.
     * @return A ResponseEntity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        signingsService.deleteSigning(id);
        return ResponseEntity.noContent().build();
    }
}