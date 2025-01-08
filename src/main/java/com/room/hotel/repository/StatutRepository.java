package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Statut;

public interface StatutRepository extends JpaRepository<Statut, UUID> {
    Optional<Statut> findByNom(String nom);

    @Query("SELECT COUNT(c) > 0 FROM Chambre c WHERE c.statut.id = :id")
    boolean isStateLinkedToChambre(@Param("id") UUID id);

    @Query("SELECT COUNT(s) > 0 FROM Salle s WHERE s.statut.id = :id")
    boolean isStateLinkedToSalle(@Param("id") UUID id);

    @Query("SELECT COUNT(o) > 0 FROM Objet o WHERE o.statut.id = :id")
    boolean isStateLinkedToObjet(@Param("id") UUID id);

    @Query("SELECT COUNT(pn) > 0 FROM Panne pn WHERE pn.statut.id = :id")
    boolean isStateLinkedToPanne(@Param("id") UUID id);

    @Query("SELECT COUNT(pa) > 0 FROM Passe pa WHERE pa.statut.id = :id")
    boolean isStateLinkedToPasse(@Param("id") UUID id);
}