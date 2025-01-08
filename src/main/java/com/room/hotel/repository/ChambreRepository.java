package com.room.hotel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Chambre;

public interface ChambreRepository extends JpaRepository<Chambre, UUID> {

    /**** Requête pour récuperer une chambre par son numéro ***/
    @Query("SELECT c FROM Chambre c WHERE c.numero = :numero ")
    Optional<Chambre> findByNumero(@Param("numero") String numero);

    @Query("SELECT c FROM Chambre c JOIN c.statut s WHERE s.nom = :nom")
    List<Chambre> getByState(@Param("nom") String nom);
}
