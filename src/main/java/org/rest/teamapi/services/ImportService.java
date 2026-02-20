package org.rest.teamapi.services;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.rest.teamapi.models.*;
import org.rest.teamapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Slf4j
@Service
public class ImportService {

    @Autowired private TeamRepository teamRepo;
    @Autowired private PlayerRepository playerRepo;
    @Autowired private SigningsRepository signingRepo;

    /**
     * Reads and parses 'Players_3.csv' to populate the Players table.
     * The method handles data type conversions for dates and numeric values.
     * * @throws Exception if the CSV file cannot be read or parsed correctly.
     */
    public void importPlayers(InputStream inputStream) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] line;
            reader.readNext(); // Skipping the CSV header

            while ((line = reader.readNext()) != null) {
                PlayerModel p = new PlayerModel();
                p.setFirstName(line[1]);
                p.setLastName(line[2]);
                p.setEmail(line[3]);
                // Parse String date to LocalDate and then convert to SQL Date for JPA compatibility
                p.setBirthDate(LocalDate.parse(line[4]));
                p.setPosition(line[5]);
                p.setGender(line[6]);
                // Convert Double strings to Integer by casting
                p.setWeight(Double.parseDouble(line[7]));
                p.setHigh(Double.parseDouble(line[8]));
                p.setImc(Double.parseDouble(line[9]));
                p.setFat(Double.parseDouble(line[10]));

                playerRepo.save(p);
            }
            log.debug("Players imported successfully.");
        }
    }

    /**
     * Reads and parses 'Teams_3.csv' to populate the Teams table.
     * Teams must be imported before Signings due to foreign key constraints.
     * * @throws Exception if the CSV file cannot be read.
     */
    public void importTeams(InputStream inputStream) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] line;
            reader.readNext(); // Skipping the CSV header

            while ((line = reader.readNext()) != null) {
                TeamModel team = new TeamModel();
                team.setName(line[1]);
                team.setEmail(line[2]);
                team.setSince(LocalDate.parse(line[3]));
                team.setCity(line[4]);

                teamRepo.save(team);
            }
            log.debug("Teams imported successfully.");
        }
    }

    /**
     * Reads 'Signings_3.csv' and links Players with Teams.
     * This method performs lookups in the database to retrieve existing entities
     * based on the IDs provided in the CSV.
     * * Logic:
     * 1. Read dates for the contract period.
     * 2. Find the Team by ID; if not found, it sets null (optional handling).
     * 3. Find the Player by ID; if not found, it sets null.
     * 4. Save the relationship in the 'Signings' table.
     * * @throws Exception if data parsing fails.
     */
    public void importSignings(InputStream inputStream) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            String[] line;
            reader.readNext(); // Skipping the CSV header

            while ((line = reader.readNext()) != null) {
                SigningsModel s = new SigningsModel();
                s.setSince(LocalDate.parse(line[3]));
                s.setUntil(LocalDate.parse(line[4]));

                // Fetch full entity objects to satisfy JPA @ManyToOne requirements
                s.setTeam(teamRepo.findById(Integer.parseInt(line[2])).orElse(null));
                s.setPlayer(playerRepo.findById(Integer.parseInt(line[1])).orElse(null));

                signingRepo.save(s);
            }
            log.debug("Signings relationship mapping completed.");
        }
    }
}