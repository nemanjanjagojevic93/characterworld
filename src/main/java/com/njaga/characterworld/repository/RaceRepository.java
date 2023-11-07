package com.njaga.characterworld.repository;

import com.njaga.characterworld.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Optional<Race> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
