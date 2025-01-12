package com.room.hotel.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.ChambreDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.ChambreMapper;
import com.room.hotel.model.Categorie;
import com.room.hotel.model.Chambre;
import com.room.hotel.model.Statut;
import com.room.hotel.repository.CategorieRepository;
import com.room.hotel.repository.ChambreRepository;
import com.room.hotel.repository.StatutRepository;
import com.room.hotel.request.ChambreRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChambreService {

    private final ChambreRepository repository;
    private final CategorieRepository categorieRepository;
    private final StatutRepository statutRepository;
    private final ChambreMapper mapper;

    /**** Créer une chambre ****/
    @Transactional
    public ChambreDto createChambre(ChambreRequest request) throws IOException {
        if (repository.findByNumero(request.getNumero()).isPresent()) {
            throw new RuntimeException("Ce numéro de chambre existe déjà dans votre établissement!");
        }
        Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));

        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));

        Chambre chambre = new Chambre();
        chambre.setNumero(request.getNumero());
        chambre.setLocalisation(request.getLocalisation());
        chambre.setCategorie(categorie);
        chambre.setStatut(statut);

        Chambre savedChambre = repository.save(chambre);
        return mapper.toDto(savedChambre);
    }

    /**** Modifier une chambre ****/
    @Transactional
    public ChambreDto updateChambre(UUID id, ChambreRequest request) throws IOException {
        Chambre oldChambre = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune chambre trouvée!"));
        if (!Objects.equals(request.getNumero(), oldChambre.getNumero()) && repository.findByNumero(request.getNumero()).isPresent()) {
            throw new RuntimeException("Ce numéro de chambre existe déjà dans votre établissement!");
        }
        Categorie categorie = categorieRepository.findByNom(request.getCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Aucune catégorie trouvée!"));
        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldChambre.setNumero(request.getNumero());
        oldChambre.setLocalisation(request.getLocalisation());
        oldChambre.setStatut(statut);
        oldChambre.setCategorie(categorie);
        Chambre updatedChambre = repository.save(oldChambre);
        return mapper.toDto(updatedChambre);
    }

    /**** Modifier seulement l'état d'une chambre ****/
    @Transactional
    public ChambreDto updateChambreEtat(UUID id, String statut) throws IOException {
        Chambre oldChambre = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune chambre trouvée!"));
        // Vérification si le statut existe, sinon création
        Statut etat = statutRepository.findByNom(statut)
        .orElseGet(() -> {
            // Création et retour du nouveau statut
            Statut newStatut = new Statut();
            newStatut.setNom(statut);
            return statutRepository.save(newStatut);
        });
        oldChambre.setStatut(etat);
        Chambre updatedChambre = repository.save(oldChambre);
        return mapper.toDto(updatedChambre);
    }

    /**** Supprimer une chambre ****/
    @Transactional
    public void deleteChambre(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune chambre trouvée");
        }
        repository.deleteById(id);
    }

    /**** Récupérer une chambre par son ID ****/
    public ChambreDto getChambre(UUID id) {
        Chambre chambre = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre non trouvée!"));
        return mapper.toDto(chambre);
    }

    public List<ChambreDto> getChambres() {
        List<Chambre> chambres = repository.findAll();
        return chambres.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ChambreDto> getChambresByState(String nom) {
        List<Chambre> chambres = repository.getByState(nom);
        return chambres.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
