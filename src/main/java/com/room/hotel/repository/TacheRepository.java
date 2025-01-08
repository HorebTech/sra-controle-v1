package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.hotel.model.Tache;

public interface TacheRepository extends JpaRepository<Tache, UUID> {
    Optional<Tache> findByAction(String action);
}