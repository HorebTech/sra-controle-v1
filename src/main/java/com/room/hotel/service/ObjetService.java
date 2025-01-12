package com.room.hotel.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.ObjetDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.ObjetMapper;
import com.room.hotel.model.Chambre;
import com.room.hotel.model.ChambreChoisie;
import com.room.hotel.model.Objet;
import com.room.hotel.model.Salle;
import com.room.hotel.model.SalleChoisie;
import com.room.hotel.model.Statut;
import com.room.hotel.repository.ChambreChoisieRepository;
import com.room.hotel.repository.ChambreRepository;
import com.room.hotel.repository.ObjetRepository;
import com.room.hotel.repository.SalleChoisieRepository;
import com.room.hotel.repository.SalleRepository;
import com.room.hotel.repository.StatutRepository;
import com.room.hotel.request.ObjetRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObjetService {

    private final ObjetRepository repository;
    private final ObjetMapper mapper;
    private final ChambreChoisieRepository chambreChoisieRepository;
    private final SalleChoisieRepository salleChoisieRepository;
    private final ChambreRepository chambreRepository;
    private final SalleRepository salleRepository;
    private final ImageService imageService;
    private final StatutRepository statutRepository;
    private static final String UPLOAD_DIR = "uploads/";

    @Transactional
    public ObjetDto create(ObjetRequest request) throws IOException {
        UUID Id = UUID.fromString(request.getId());

        ChambreChoisie chambreChoisie = chambreChoisieRepository.findById(Id).isPresent() ? chambreChoisieRepository.findById(Id).get() : null;
        SalleChoisie salleChoisie = salleChoisieRepository.findById(Id).isPresent() ? salleChoisieRepository.findById(Id).get() : null;

        /* Initialize Object and joined present entities ***/
        Objet objet = new Objet();
        objet.setChambreChoisie(chambreChoisie);
        objet.setSalleChoisie(salleChoisie);

        // Vérification si le statut existe, sinon création
        Statut statut = statutRepository.findByNom(request.getStatut())
        .orElseGet(() -> {
            // Création et retour du nouveau statut
            Statut newStatut = new Statut();
            newStatut.setNom(request.getStatut());
            return statutRepository.save(newStatut);
        });

        /* Define the properties of the object ***/
        objet.setDescription(request.getDescription());

        /* Manage picture ****/
        String nomImage = imageService.saveImage(request.getImageBase64(), "objets");
        // String nomImage = saveImage(request.getImageBase64());
        objet.setPhoto(nomImage);
        objet.setStatut(statut);
        /* Save Object et return DTO ***/
        return mapper.toDto(repository.save(objet));
    }

    public ObjetDto getObjet(UUID id) {
        Objet objet = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found!"));
        return mapper.toDto(objet);
    }

    /**** Modifier l'état d'un problème technique ****/
    @Transactional
    public ObjetDto updateState(UUID id, ObjetRequest request) throws IOException {
        Objet oldObjet = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun trouvé trouvé avec cet ID!"));
        // Vérification si le statut existe, sinon création
        Statut statut = statutRepository.findByNom(request.getStatut())
        .orElseGet(() -> {
            // Création et retour du nouveau statut
            Statut newStatut = new Statut();
            newStatut.setNom(request.getStatut());
            return statutRepository.save(newStatut);
        });
        oldObjet.setStatut(statut);
        if(request.getDescription() != null){
            oldObjet.setDescription(request.getDescription());
        }
        Objet updated = repository.save(oldObjet);
        return mapper.toDto(updated);
    }

    /**** Récupérer tous les Objets dans un chambre en fonction d'un état ****/
    @SuppressWarnings("null")
    public List<ObjetDto> getObjetsByState(String statut, String numero) {
        Chambre chambre = chambreRepository.findByNumero(numero).isPresent() ? chambreRepository.findByNumero(numero).get() : null;
        Salle salle = salleRepository.findByNumero(numero).isPresent() ? salleRepository.findByNumero(numero).get() : null;
        List<Objet> objets = null;
        if(chambre.getId() != null) {
            objets = repository.getByStateAndRoom(statut, chambre.getNumero());
        } else if(salle.getId() != null) {
            objets = repository.getByStateAndHall(statut, salle.getNumero());
        }
        return objets.stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    public List<ObjetDto> getAllObjet() {
        List<Objet> objets = repository.findAll();
        return objets.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ObjetDto deleteObjet(UUID id) throws IOException {
        Objet oldObjet = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oups, un problème est survenu. L'objet n'existe peut-être pas."));
        Path path = Paths.get(UPLOAD_DIR + oldObjet.getPhoto());
        Files.delete(path);
        repository.deleteById(id);
        return mapper.toDto(oldObjet);
    }

}
