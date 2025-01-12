package com.room.hotel.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.SalleChoisieDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.SalleChoisieMapper;
import com.room.hotel.model.Passe;
import com.room.hotel.model.Salle;
import com.room.hotel.model.SalleChoisie;
import com.room.hotel.repository.PasseRepository;
import com.room.hotel.repository.SalleChoisieRepository;
import com.room.hotel.repository.SalleRepository;
import com.room.hotel.request.SalleChoisieRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalleChoisieService {

    private final SalleChoisieRepository repository;
    private final SalleRepository salleRepository;
    private final SalleService salleService;
    private final PasseRepository passeRepository;
    private final SalleChoisieMapper mapper;

    @Transactional
    public SalleChoisieDto createSalleChoisie(SalleChoisieRequest request) throws IOException {

        Passe passe = passeRepository.findById(request.getPasseId()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        Salle salle = salleRepository.findByNumero(request.getNumero()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));

        SalleChoisie salleChoisie = new SalleChoisie();
        salleChoisie.setPasse(passe);
        salleChoisie.setSalle(salle);
        
        salleService.updateSalleEtat(salle.getId(), "Attribué");

        SalleChoisie savedSalleChoisie = repository.save(salleChoisie);
        return mapper.toDto(savedSalleChoisie);
    }

    public SalleChoisieDto getSalleChoisie(UUID id) {
        SalleChoisie salleChoisie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("erreur!"));
        return mapper.toDto(salleChoisie);
    }

    public List<SalleChoisieDto> getAllSallesChoisies() {
        List<SalleChoisie> salleChoisie = repository.findAll();
        return salleChoisie.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSalleChoisie(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune Salle trouvée");
        }
        repository.deleteById(id);
    }

    @Transactional
    public SalleChoisieDto updateState(UUID salleChoisieId, SalleChoisieRequest request) throws IOException {
        SalleChoisie oldSalleChoisie = repository.findById(salleChoisieId)
                .orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur à été trouvée!"));
        Date date = new Date();
        if(Objects.equals(request.getStatut(), "En cours")){
            LocalDateTime heureDebutLocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalTime heureDebut = heureDebutLocalDateTime.toLocalTime();
            oldSalleChoisie.setHeureDebut(heureDebut);
        }
        if(Objects.equals(request.getStatut(), "Propre")){
            LocalDateTime heureFinLocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalTime heureFin = heureFinLocalDateTime.toLocalTime();
            oldSalleChoisie.setHeureFin(heureFin);

            Duration duration = Duration.between(oldSalleChoisie.getHeureDebut(), heureFin);
            long secondes = duration.toSecondsPart();
            long minutes = duration.toMinutesPart();
            long heures = duration.toHours();
            oldSalleChoisie.setDuree(heures+" h "+minutes+" min "+secondes+" s");
        }
        UUID salleId = oldSalleChoisie.getSalle().getId();
        salleService.updateSalleEtat(salleId, request.getStatut());
        SalleChoisie updatedSalleChoisie = repository.save(oldSalleChoisie);
        return mapper.toDto(updatedSalleChoisie);
    }
}
