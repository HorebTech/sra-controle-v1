package com.room.hotel.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Objet;

public interface ObjetRepository extends JpaRepository<Objet, UUID> {
        /*** Requête pour récuperer tous objets en fonction d'un état et dans une chambre ***/
    @Query("SELECT o FROM Objet o WHERE o.statut.nom = :statut AND o.chambreChoisie.chambre.numero = :numeroChambre")
    List<Objet> getByStateAndRoom(@Param("statut") String statut, @Param("numeroChambre") String numeroChambre);

    @Query("SELECT o FROM Objet o WHERE o.statut.nom = :statut AND o.salleChoisie.salle.numero = :numeroSalle")
    List<Objet> getByStateAndHall(@Param("statut") String statut, @Param("numeroSalle") String numeroSalle);

}