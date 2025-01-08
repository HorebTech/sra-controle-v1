package com.room.hotel.service;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.room.hotel.mapper.SalleMapper;
import com.room.hotel.model.Statut;
import com.room.hotel.repository.StatutRepository;
import org.springframework.stereotype.Service;

import com.room.hotel.dto.SalleDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.model.Salle;
import com.room.hotel.repository.SalleRepository;
import com.room.hotel.request.SalleRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalleService {

    private final SalleRepository repository;
    private final StatutRepository statutRepository;
    private final SalleMapper mapper;


    /**** Créer une salle ****/
    @Transactional
    public SalleDto createSalle(SalleRequest request) throws IOException {
        if (repository.findByNumero(request.getNumero()).isPresent()) {
            throw new RuntimeException("Ce numéro de salle existe déjà dans votre établissement!");
        }
        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        Salle salle = new Salle();
        salle.setNumero(request.getNumero());
        salle.setDescription(request.getDescription());
        salle.setStatut(statut);
        Salle savedSalle = repository.save(salle);
        return mapper.toDto(savedSalle);
    }

    /**** Modifier une Salle ****/
    @Transactional
    public SalleDto updateSalle(UUID id, SalleRequest request) throws IOException {
        Salle oldSalle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune Salle trouvée!"));

        if (!Objects.equals(request.getNumero(), oldSalle.getNumero()) && repository.findByNumero(request.getNumero()).isPresent()) {
            throw new RuntimeException("Ce numéro de salle existe déjà dans votre établissement!");
        } else if(Objects.equals(request.getNumero(), oldSalle.getNumero())) {
            oldSalle.setNumero(request.getNumero());
        } else{
            oldSalle.setNumero(request.getNumero());
        }
        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldSalle.setDescription(request.getDescription());
        oldSalle.setStatut(statut);
        Salle updatedSalle = repository.save(oldSalle);
        return mapper.toDto(updatedSalle);
    }

    /**** Modifier seulement l'état d'une salle ****/
    @Transactional
    public SalleDto updateSalleEtat(UUID id, String statut) throws IOException {
        Salle oldSalle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune salle trouvée!"));
        Statut state = statutRepository.findByNom(statut)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldSalle.setStatut(state);
        Salle updatedSalle = repository.save(oldSalle);
        return mapper.toDto(updatedSalle);
    }

    /**** Supprimer une salle ****/
    @Transactional
    public void deleteSalle(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune salle trouvée");
        }
        repository.deleteById(id);
    }

    /**** Récupérer une salle par son ID ****/
    public SalleDto getSalle(UUID id) {
        Salle salle = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée!"));
        return mapper.toDto(salle);
    }


    public List<SalleDto> getSalles() {
        List<Salle> salles = repository.findAll();
        return salles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<SalleDto> getSallesByState(String nom) {
        List<Salle> salles = repository.getByState(nom);
        return salles.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
