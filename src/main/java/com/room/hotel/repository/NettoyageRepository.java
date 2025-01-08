package com.room.hotel.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.hotel.model.Nettoyage;

public interface NettoyageRepository extends JpaRepository<Nettoyage, UUID> {
    Optional<Nettoyage> findByAction(String action);
}
