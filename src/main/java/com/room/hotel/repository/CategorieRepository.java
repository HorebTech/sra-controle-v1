package com.room.hotel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, UUID> {
    Optional<Categorie> findByNom(String nom);

    @Query("SELECT COUNT(t) > 0 FROM Tache t WHERE t.categorie.id = :id")
    boolean isCategorieLinkedToTache(@Param("id") UUID id);

    @Query("SELECT COUNT(c) > 0 FROM Chambre c WHERE c.categorie.id = :id")
    boolean isCategorieLinkedToChambre(@Param("id") UUID id);

    @Query("SELECT COUNT(n) > 0 FROM Nettoyage n WHERE n.categorie.id = :id")
    boolean isCategorieLinkedToNettoyage(@Param("id") UUID id);
}