package com.room.hotel.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.PanneCountByMarqueDto;
import com.room.hotel.dto.PanneDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.PanneMapper;
import com.room.hotel.model.Chambre;
import com.room.hotel.model.ChambreChoisie;
import com.room.hotel.model.Marque;
import com.room.hotel.model.Panne;
import com.room.hotel.model.Salle;
import com.room.hotel.model.SalleChoisie;
import com.room.hotel.model.Statut;
import com.room.hotel.repository.ChambreChoisieRepository;
import com.room.hotel.repository.ChambreRepository;
import com.room.hotel.repository.MarqueRepository;
import com.room.hotel.repository.PanneRepository;
import com.room.hotel.repository.SalleChoisieRepository;
import com.room.hotel.repository.SalleRepository;
import com.room.hotel.repository.StatutRepository;
import com.room.hotel.request.PanneRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PanneService {

    private final PanneRepository repository;
    private final ChambreRepository chambreRepository;
    private final SalleRepository salleRepository;
    private final PanneMapper mapper;
    private final ChambreChoisieRepository chambreChoisieRepository;
    private final StatutRepository statutRepository;
    private final SalleChoisieRepository salleChoisieRepository;
    private final MarqueRepository marqueRepository;
    private final ImageService imageService;
    private static final String UPLOAD_DIR = "uploads/";

    @Transactional
    public PanneDto create(PanneRequest request) throws IOException {
        UUID Id = UUID.fromString(request.getId());

        ChambreChoisie chambreChoisie = chambreChoisieRepository.findById(Id).isPresent() ? chambreChoisieRepository.findById(Id).get() : null;
        SalleChoisie salleChoisie = salleChoisieRepository.findById(Id).isPresent() ? salleChoisieRepository.findById(Id).get() : null;
        Marque marque =  marqueRepository.findByNom(request.getMarqueEquipement()).isPresent() ? marqueRepository.findByNom(request.getMarqueEquipement()).get(): null;

        /* Initialize Pane and joined present entities ***/
        Panne panne = new Panne();
        panne.setChambreChoisie(chambreChoisie);
        panne.setSalleChoisie(salleChoisie);

        // Vérification si le statut existe, sinon création
        Statut statut = statutRepository.findByNom(request.getStatut())
        .orElseGet(() -> {
            // Création et retour du nouveau statut
            Statut newStatut = new Statut();
            newStatut.setNom(request.getStatut());
            return statutRepository.save(newStatut);
        });

        panne.setStatut(statut);
        panne.setDescription(request.getDescription());
        panne.setNomEquipement(request.getNomEquipement());
        panne.setDate(request.getDate());
        panne.setMarqueEquipement(marque);


        /* Manage picture ****/
        String nomImage = imageService.saveImage(request.getImageBase64(), "pannes");
        // String nomImage = saveImage(request.getImageBase64());
        panne.setPhoto(nomImage);

        return mapper.toDto(repository.save(panne));
    }

    public PanneDto get(UUID id) {
        Panne oldPanne = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Panne non trouvée!"));
        return mapper.toDto(oldPanne);
    }

    public List<PanneDto> getAll() {
        List<Panne> pannes = repository.findAll();
        return pannes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**** Récupérer toutes les pannes du jour ****/
    public List<PanneDto> getByDay(String creationDateString) {
        List<Panne> pannes = repository.findByDate(creationDateString);
        return pannes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PanneDto> findBetweenDates(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Panne> pannes = repository.findBetweenDates(dateDebut, dateFin);
        return pannes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**** Modifier l'état d'un problème technique ****/
    @Transactional
    public PanneDto updateState(UUID id, PanneRequest request) throws IOException {
        
        Panne oldPanne = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun problème technique trouvé avec cet ID!"));

        if(request.getNomEquipement() != null){
            oldPanne.setNomEquipement(request.getNomEquipement());
        }
        
        Statut statut = statutRepository.findByNom(request.getStatut())
        .orElseGet(() -> {
            // Création et retour du nouveau statut
            Statut newStatut = new Statut();
            newStatut.setNom(request.getStatut());
            return statutRepository.save(newStatut);
        });
        oldPanne.setStatut(statut);

        if(request.getDescription() != null){
            oldPanne.setDescription(request.getDescription());
        }
        oldPanne.setMarqueEquipement(oldPanne.getMarqueEquipement());

        Panne updated = repository.save(oldPanne);
        return mapper.toDto(updated);
    }

    @Transactional
    public PanneDto delete(UUID id) throws IOException {
        Panne oldPanne = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oups, un problème est survenu. Le problème technique n'existe peut-être pas!"));
        Path path = Paths.get(UPLOAD_DIR+ oldPanne.getPhoto());
        Files.delete(path);
        repository.deleteById(id);
        return mapper.toDto(oldPanne);
    }

        /**** Récupérer tous les Objets dans un chambre en fonction d'un état ****/
    @SuppressWarnings("null")
    public List<PanneDto> getPannesByState(String statut, String numero) {
        Chambre chambre = chambreRepository.findByNumero(numero).isPresent() ? chambreRepository.findByNumero(numero).get() : null;
        Salle salle = salleRepository.findByNumero(numero).isPresent() ? salleRepository.findByNumero(numero).get() : null;
        List<Panne> pannes = null;
        if(chambre.getId() != null) {
            pannes = repository.getByStateAndRoom(statut, chambre.getNumero());
        } else if(salle.getId() != null) {
            pannes = repository.getByStateAndHall(statut, salle.getNumero());
        }
        return pannes.stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    public List<Object[]> getByRoomBetweenDates(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return repository.findRoomBetweenDates(dateDebut, dateFin);
    }

    public List<PanneCountByMarqueDto> getPannesCountByMarqueIncludingEmpty() {
        List<Object[]> results = repository.countPannesByMarqueIncludingEmpty();
    
        // Transformation des résultats
        return results.stream()
                .map(result -> {
                    if (result[0] != null && result[1] != null) {
                        String marque = String.valueOf(result[0]); // Convertit explicitement en String
                        Long total = ((Number) result[1]).longValue(); // Convertit explicitement en Long
                        return new PanneCountByMarqueDto(marque, total);
                    } else {
                        throw new IllegalArgumentException("Les données retournées par la requête sont invalides : " + Arrays.toString(result));
                    }
                })
                .collect(Collectors.toList());
    }
    

}
