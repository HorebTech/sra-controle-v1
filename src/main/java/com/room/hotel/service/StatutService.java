package com.room.hotel.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.StatutDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.StatutMapper;
import com.room.hotel.model.Statut;
import com.room.hotel.repository.StatutRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatutService {
    private final StatutRepository repository;
    private final StatutMapper mapper;

    @Transactional
    public StatutDto createStatut(StatutDto dto) throws IOException {
        if (repository.findByNom(dto.getNom()).isPresent()) {
            throw new ResourceNotFoundException("Cet état existe déjà !");
        }
        Statut statut = mapper.toEntity(dto);
        Statut savedStatut = repository.save(statut);
        return mapper.toDto(savedStatut);
    }

    @Transactional
    public void deleteStatut(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucun Statut trouvé!");
        }
        // Vérifie si le statut est associé à des éléments
        boolean isLinkedToSalle = repository.isStateLinkedToSalle(id);
        boolean isLinkedToChambre = repository.isStateLinkedToChambre(id);
        boolean isLinkedToObjet = repository.isStateLinkedToObjet(id);
        boolean isLinkedToPanne = repository.isStateLinkedToPanne(id);
        boolean isLinkedToPasse = repository.isStateLinkedToPasse(id);
        if (isLinkedToSalle || isLinkedToChambre || isLinkedToObjet || isLinkedToPanne || isLinkedToPasse) {
            throw new IllegalStateException("Cet état est associé à un ou plusieurs éléments et ne peut pas être supprimé. Vous pouvez par contre modifier cet état.");
        }
        repository.deleteById(id);
    }

    public StatutDto getStatut(UUID id) {
        Statut statut = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Statut non trouvé!"));
        return mapper.toDto(statut);
    }

    /**** Modifier une catégorie ****/
    @Transactional
    public StatutDto updateStatut(UUID id, StatutDto statutDto) throws IOException {
        Statut oldStatut = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldStatut.setNom(statutDto.getNom());
        Statut updatedStatut = repository.save(oldStatut);
        return mapper.toDto(updatedStatut);
    }

    public List<StatutDto> findAllStatuts() {
        List<Statut> statuts = repository.findAll();
        return statuts.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
