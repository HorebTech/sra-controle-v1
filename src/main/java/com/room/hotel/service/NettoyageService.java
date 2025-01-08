package com.room.hotel.service;


import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.NettoyageDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.NettoyageMapper;
import com.room.hotel.model.Categorie;
import com.room.hotel.model.Nettoyage;
import com.room.hotel.repository.CategorieRepository;
import com.room.hotel.repository.NettoyageRepository;
import com.room.hotel.request.NettoyageRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NettoyageService {

    private final NettoyageRepository repository;
    private final CategorieRepository categorieRepository;
    private final NettoyageMapper mapper;

    @Transactional
    public NettoyageDto createNettoyage(NettoyageRequest request) throws IOException {
        if(repository.findByAction(request.getAction()).isPresent()) {
            throw new ResourceNotFoundException("Un nettoyage avec cette description existe déjà!");
        }
        Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
        Nettoyage nettoyage = new Nettoyage();
        nettoyage.setAction(request.getAction());
        nettoyage.setCategorie(categorie);
        Nettoyage savedNettoyage = repository.save(nettoyage);
        return mapper.toDto(savedNettoyage);
    }

    @Transactional
    public void deleteNettoyage(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucun Nettoyage trouvé avec cet ID!");
        }
        repository.deleteById(id);
    }

    public NettoyageDto getNettoyage(UUID id) {
        Nettoyage nettoyage = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nettoyage non trouvé avec cet ID!"));
        return mapper.toDto(nettoyage);
    }

    @Transactional
    public NettoyageDto updateNettoyage(UUID id, NettoyageRequest request) throws IOException {
        Nettoyage oldNettoyage = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun néttoyage trouvé avec cet ID!"));

        Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));

        oldNettoyage.setAction(request.getAction());
        oldNettoyage.setCategorie(categorie);

        Nettoyage updatedNettoyage = repository.save(oldNettoyage);
        return mapper.toDto(updatedNettoyage);
    }

    public List<NettoyageDto> findAllNettoyages() {
        List<Nettoyage> nettoyages = repository.findAll();
        return nettoyages.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
