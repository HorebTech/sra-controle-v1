package com.room.hotel.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.ChambreChoisieDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.ChambreChoisieMapper;
import com.room.hotel.model.Chambre;
import com.room.hotel.model.ChambreChoisie;
import com.room.hotel.model.Passe;
import com.room.hotel.repository.ChambreChoisieRepository;
import com.room.hotel.repository.ChambreRepository;
import com.room.hotel.repository.PasseRepository;
import com.room.hotel.request.ChambreChoisieRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChambreChoisieService {

    private final ChambreChoisieRepository repository;
    private final ChambreRepository chambreRepository;
    private final ChambreService chambreService;
    private final PasseRepository passeRepository;
    private final ChambreChoisieMapper mapper;

    @Transactional
    public ChambreChoisieDto createChambreChoisie(ChambreChoisieRequest request) throws IOException {

        Passe passe = passeRepository.findById(request.getPasseId()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        Chambre chambre = chambreRepository.findByNumero(request.getNumero()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));

        ChambreChoisie chambreChoisie = new ChambreChoisie();
        chambreChoisie.setPasse(passe);
        chambreChoisie.setChambre(chambre);

        ChambreChoisie savedChambreChoisie = repository.save(chambreChoisie);
        return mapper.toDto(savedChambreChoisie);
    }

    public ChambreChoisieDto getChambreChoisie(UUID id) {
        ChambreChoisie chambreChoisie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("erreur!"));
        return mapper.toDto(chambreChoisie);
    }

    public List<ChambreChoisieDto> getAllChambresChoisies() {
        List<ChambreChoisie> chambreChoisie = repository.findAll();
        return chambreChoisie.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteChambreChoisie(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune chambre trouvée");
        }
        repository.deleteById(id);
    }

    @Transactional
    public ChambreChoisieDto updateChambreChoisieState(UUID chambreChoisieId, ChambreChoisieRequest request) throws IOException {
        ChambreChoisie oldChambreChoisie = repository.findById(chambreChoisieId)
                .orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur a été trouvée!"));
        
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());
        switch (request.getStatut()) {
            case "En cours" -> oldChambreChoisie.setHeureDebut(currentDateTime.toLocalTime());

            case "Propre" -> {
                LocalTime heureFin = currentDateTime.toLocalTime();
                oldChambreChoisie.setHeureFin(heureFin);

                if (oldChambreChoisie.getHeureDebut() != null) {
                    Duration duration = Duration.between(oldChambreChoisie.getHeureDebut(), heureFin);
                    oldChambreChoisie.setDuree(formatDuration(duration));
                } else {
                    throw new IllegalStateException("Heure de début manquante pour calculer la durée.");
                }
            }

            default -> throw new IllegalArgumentException("Statut non reconnu : " + request.getStatut());
        }

        UUID chambreId = oldChambreChoisie.getChambre().getId();
        chambreService.updateChambreEtat(chambreId, request.getStatut());

        ChambreChoisie updatedChambreChoisie = repository.save(oldChambreChoisie);
        return mapper.toDto(updatedChambreChoisie);
    }

    private String formatDuration(Duration duration) {
        long heures = duration.toHours();
        long minutes = duration.toMinutesPart();
        long secondes = duration.toSecondsPart();
        return String.format("%d h %d min %d s", heures, minutes, secondes);
    }
}
