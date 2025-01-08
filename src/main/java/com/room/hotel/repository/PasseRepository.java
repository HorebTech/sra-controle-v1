package com.room.hotel.repository;

import com.room.hotel.model.Passe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasseRepository  extends JpaRepository<Passe, Long> {

    /*** Requête pour récuperer tous les passes par état***/
    @Query("SELECT p FROM Passe p JOIN p.statut s WHERE s.nom = :nom")
    List<Passe> getByState(@Param("nom") String nom);

    /*** Requête pour récuperer tous les passes par état d'un utilisateur par son nom***/
    @Query("SELECT p FROM Passe p JOIN p.statut s JOIN p.agent a WHERE s.nom = :nom AND a.nom = :agent")
    List<Passe> getByStateAndName(@Param("nom") String nom, @Param("agent") String agent);

    /*** Requête pour récuperer tous les passes par état d'un utilisateur par son nom et la date du jour***/
    @Query("SELECT p FROM Passe p JOIN p.statut s JOIN p.agent a WHERE s.nom = :nom AND a.nom = :agent AND p.dateNettoyage = :date")
    List<Passe> getByStateAndNameAndDate(@Param("nom") String nom, @Param("agent") String agent, @Param("date") String date);

    /*** Requête pour récuperer tous les passes d'un utilisateur selon l'état NOUVEAU ou EN_COURS par son nom et la date du jour***/
    @Query("SELECT p FROM Passe p JOIN p.statut s JOIN p.agent a WHERE s.nom IN ('Nouveau', 'En cours') AND a.nom = :agent AND p.dateNettoyage = :date")
    List<Passe> getNewAndCurrent(@Param("agent") String agent, @Param("date") String date);
}