package org.rest.teamapi.controllers;


import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.services.ImportService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/import")
public class ImportController {

    private final ImportService importService;

    @PostMapping(value = "/teams", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir CSV de Jugadores", description = "Selecciona un archivo .csv desde tu computadora para cargar jugadores.")
    public ResponseEntity<MultipartFile> uploadTeam(@RequestParam("file") MultipartFile file){

        try{
            importService.importTeams(file.getInputStream());
            log.info("Teams imported successfully from file: {}", file.getOriginalFilename());
            return ResponseEntity.ok(file);

        } catch (Exception e) {
            log.error("Error importing teams: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value = "/players", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir CSV de Jugadores", description = "Selecciona un archivo .csv desde tu computadora para cargar jugadores.")
    public ResponseEntity<MultipartFile> uploadPlayers(@RequestParam("file") MultipartFile file){

        try{
            importService.importPlayers(file.getInputStream());
            log.info("Players imported successfully from file: {}", file.getOriginalFilename());
            return ResponseEntity.ok(file);

        } catch (Exception e) {
            log.error("Error importing players: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value = "/signings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir CSV de Contrataciones", description = "Selecciona un archivo .csv desde tu computadora para cargar contrataciones.")
    public ResponseEntity<MultipartFile> uploadSignings(@RequestParam("file") MultipartFile file){

        try{
            importService.importSignings(file.getInputStream());
            log.info("Signings imported successfully from file: {}", file.getOriginalFilename());
            return ResponseEntity.ok(file);

        } catch (Exception e) {
            log.error("Error importing signings: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
