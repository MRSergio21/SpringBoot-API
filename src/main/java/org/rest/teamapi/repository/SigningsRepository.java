package org.rest.teamapi.repository;

import org.rest.teamapi.models.PlayerModel;
import org.rest.teamapi.models.SigningsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface SigningsRepository extends JpaRepository<SigningsModel, Integer> {

    @Query("SELECT MAX(s.until) FROM SigningsModel s WHERE s.player.idPlayer = :playerId AND s.until >= :now")
    Optional<LocalDate> findLatestContractEndDate(Integer playerId, LocalDate now);

    @Query("SELECT s.player FROM SigningsModel s WHERE s.team.idTeam = :teamId AND :now BETWEEN s.since AND s.until")
    List<PlayerModel> findActivePlayersByTeam(Integer teamId, LocalDate now);

}
