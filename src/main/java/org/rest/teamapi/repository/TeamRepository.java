package org.rest.teamapi.repository;

import org.rest.teamapi.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Integer> {

    boolean existsByEmail(String email);

}