package com.room.hotel.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.room.hotel.dto.TacheDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.TacheMapper;
import com.room.hotel.model.Categorie;
import com.room.hotel.model.Tache;
import com.room.hotel.repository.CategorieRepository;
import com.room.hotel.repository.TacheRepository;
import com.room.hotel.request.TacheRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TacheService {

    private final TacheRepository repository;
    private final CategorieRepository categorieRepository;
    private final TacheMapper mapper;

    @Transactional
    public TacheDto saveTache(TacheRequest request) throws IOException {
        
        Tache tache = new Tache();
        if(repository.findByAction(request.getAction()).isPresent()) {
            throw new ResourceNotFoundException("Une tâche avec cette description existe déjà!");
        }
        Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));

        if(request.getSous_categorie() != null) {
            Categorie sous_categorie = categorieRepository.findByNom(request.getSous_categorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
                tache.setSous_categorie(sous_categorie);
        }
        tache.setAction(request.getAction());
        tache.setCategorie(categorie);
        Tache savedTache = repository.save(tache);
        return mapper.toDto(savedTache);
    }

    public TacheDto getTache(UUID id) {
        Tache tache = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache non trouvée!"));
        Hibernate.initialize(tache.getCategorie());
        Hibernate.initialize(tache.getSous_categorie());
        return mapper.toDto(tache);
    }

    /**** Modifier une tache ****/
    @Transactional
    public TacheDto updateTache(UUID id, TacheRequest request) throws IOException {
        Tache oldTache = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune tâche trouvée!"));
        if(request.getCategorie() != null) {
            Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                    .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
            oldTache.setCategorie(categorie);
        }
        if(request.getSous_categorie() != null) {
            Categorie sous_categorie = categorieRepository.findByNom(request.getSous_categorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
                oldTache.setSous_categorie(sous_categorie);
        }
        if(request.getAction() != null) {
            oldTache.setAction(request.getAction());
        }
        Tache updatedTache = repository.save(oldTache);
        return mapper.toDto(updatedTache);
    }

    @Transactional
    public void deleteTache(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune Tache trouvée!");
        }
        repository.deleteById(id);
    }

    public List<TacheDto> getAllTache() {
        List<Tache> taches = repository.findAll();
        return taches.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
