package com.room.hotel.repository;

import com.room.hotel.model.SalleChoisie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SalleChoisieRepository extends JpaRepository<SalleChoisie, UUID> {
    @Query("SELECT sc FROM SalleChoisie sc JOIN sc.salle s WHERE s.numero = :numero")
    Optional<SalleChoisie> getByNumero(@Param("numero") String numero);
}
