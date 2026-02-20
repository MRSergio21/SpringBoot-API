package org.rest.teamapi.repository;

import org.rest.teamapi.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Integer> {

    boolean existsByEmail(String email);
}
