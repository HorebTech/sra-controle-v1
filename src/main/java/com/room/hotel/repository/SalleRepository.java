package com.room.hotel.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Salle;

public interface SalleRepository extends JpaRepository<Salle, UUID> {

    /**** Requête pour récuperer une salle par son numéro ***/
    @Query("SELECT s FROM Salle s WHERE s.numero = :numero ")
    Optional<Salle> findByNumero(@Param("numero") String numero);

    @Query("SELECT sl FROM Salle sl JOIN sl.statut s WHERE s.nom = :nom")
    List<Salle> getByState(@Param("nom") String nom);
}
