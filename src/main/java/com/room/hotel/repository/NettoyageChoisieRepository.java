package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.NettoyageChoisie;

public interface NettoyageChoisieRepository  extends JpaRepository<NettoyageChoisie, UUID> {
    @Query("SELECT nc FROM NettoyageChoisie nc JOIN nc.nettoyage n WHERE n.action = :action")
    Optional<NettoyageChoisie> getByAction(@Param("action") String action);
}
