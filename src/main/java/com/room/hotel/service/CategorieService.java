package com.room.hotel.service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.CategorieDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.CategorieMapper;
import com.room.hotel.model.Categorie;
import com.room.hotel.repository.CategorieRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategorieService {
    private final CategorieRepository repository;
    private final CategorieMapper mapper;

    @Transactional
    public CategorieDto createCategorie(CategorieDto dto) throws IOException {
        Categorie categorie = mapper.toEntity(dto);
        Categorie savedCategorie = repository.save(categorie);
        return mapper.toDto(savedCategorie);
    }

    @Transactional
    public void deleteCategorie(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune Categorie trouvée!");
        }
            // Vérifie si la catégorie est associée à des éléments
        boolean isLinkedToTache = repository.isCategorieLinkedToTache(id);
        boolean isLinkedToChambre = repository.isCategorieLinkedToChambre(id);
        boolean isLinkedToNettoyage = repository.isCategorieLinkedToNettoyage(id);
        if (isLinkedToTache || isLinkedToChambre || isLinkedToNettoyage) {
            throw new IllegalStateException("La catégorie est associée à un ou plusieurs éléments et ne peut pas être supprimée. Vous pouvez par contre modifier cette catégorie.");
        }
        repository.deleteById(id);
    }

    public CategorieDto getCategorie(UUID id) {
        Categorie Categorie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie non trouvée!"));
        return mapper.toDto(Categorie);
    }

    /**** Modifier une catégorie ****/
    @Transactional
    public CategorieDto updateCategorie(UUID id, CategorieDto categorieDto) throws IOException {
        Categorie oldCategorie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
        oldCategorie.setNom(categorieDto.getNom());
        Categorie updatedCategorie = repository.save(oldCategorie);
        return mapper.toDto(updatedCategorie);
    }

    public List<CategorieDto> findAllCategories() {
        List<Categorie> categories = repository.findAll();
        return categories.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
