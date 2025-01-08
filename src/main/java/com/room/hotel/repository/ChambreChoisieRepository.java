package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.room.hotel.model.ChambreChoisie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChambreChoisieRepository extends JpaRepository<ChambreChoisie, UUID> {
    @Query("SELECT cc FROM ChambreChoisie cc JOIN cc.chambre c WHERE c.numero = :numero")
    Optional<ChambreChoisie> getByNumero(@Param("numero") String numero);
}
