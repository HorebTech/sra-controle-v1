package com.room.hotel.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.room.hotel.model.Panne;

public interface PanneRepository extends JpaRepository<Panne, UUID> {

    @Query("SELECT p FROM Panne p WHERE p.creationDate BETWEEN :dateDebut AND :dateFin")
    List<Panne> findBetweenDates(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);

    @Query("SELECT p FROM Panne p WHERE p.date = :creationDateString")
    List<Panne> findByDate(@Param("creationDateString") String creationDateString);

        @Query("SELECT p FROM Panne p WHERE p.statut.nom = :statut AND p.chambreChoisie.chambre.numero = :numeroChambre")
    List<Panne> getByStateAndRoom(@Param("statut") String statut, @Param("numeroChambre") String numeroChambre);

    @Query("SELECT p FROM Panne p WHERE p.statut.nom = :statut AND p.salleChoisie.salle.numero = :numeroSalle")
    List<Panne> getByStateAndHall(@Param("statut") String statut, @Param("numeroSalle") String numeroSalle);

    /*** Requête pour récuperer la chambre avec le maximum de pannes **/
    @Query("SELECT p.chambreChoisie.chambre.numero, COUNT(p) AS nombrePannes FROM Panne p WHERE p.creationDate BETWEEN :dateDebut AND :dateFin GROUP BY p.chambreChoisie.chambre.numero HAVING COUNT(p) = (SELECT MAX(nombrePannes) FROM (SELECT COUNT(p2) AS nombrePannes FROM Panne p2 WHERE p2.creationDate BETWEEN :dateDebut AND :dateFin GROUP BY p2.chambreChoisie.chambre.numero))")
    List<Object[]> findRoomBetweenDates(@Param("dateDebut") LocalDateTime dateDebut, @Param("dateFin") LocalDateTime dateFin);

//     @Query("SELECT p.chambreChoisie.chambre.numero, COUNT(p) AS nombrePannes " +
//     "FROM Panne p " +
//     "GROUP BY p.chambreChoisie.chambre.numero " +
//     "HAVING COUNT(p) = ( " +
//     "    SELECT MAX(nombrePannes) " +
//     "    FROM ( " +
//     "        SELECT COUNT(p2) AS nombrePannes " +
//     "        FROM Panne p2 " +
//     "        GROUP BY p2.chambreChoisie.chambre.numero " +
//     "    ) " +
//     ")")
// List<Object[]> findRoomWithMaxPannes();

@Query("SELECT p.chambreChoisie.chambre.numero, COUNT(p) AS nombrePannes " +
       "FROM Panne p " +
       "GROUP BY p.chambreChoisie.chambre.numero " +
       "ORDER BY COUNT(p) DESC")
List<Object[]> findTopChambresWithMostPannes(Pageable pageable);

    @Query("SELECT p.marqueEquipement.nom, COUNT(p) FROM Panne p GROUP BY p.marqueEquipement.nom")
    List<Object[]> countPannesByMarque();

    @Query("SELECT m.nom, COUNT(p) " +
    "FROM Marque m " +
    "LEFT JOIN Panne p ON p.marqueEquipement.id = m.id " +
    "GROUP BY m.nom")
    List<Object[]> countPannesByMarqueIncludingEmpty();
}
