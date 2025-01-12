package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.room.hotel.model.Marque;

public interface MarqueRepository extends JpaRepository<Marque, UUID> {
    Optional<Marque> findByNom(String nom);
}