package org.apirest.talky.repositories;

import org.apirest.talky.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PageRepository extends JpaRepository<PageEntity, Long> {

    Optional<PageEntity> findByTitle(String tittle);
    Boolean existsByTitle(String title);

    @Modifying
    @Query("DELETE FROM PageEntity p WHERE p.title = ?1")
    void deleteByTitle(String title);

}
