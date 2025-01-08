package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.TacheChoisie;

public interface TacheChoisieRepository extends JpaRepository<TacheChoisie, UUID> {
    @Query("SELECT tc FROM TacheChoisie tc JOIN tc.tache t WHERE t.action = :action")
    Optional<TacheChoisie> getByAction(@Param("action") String action);
}
