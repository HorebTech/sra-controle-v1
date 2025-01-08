package com.room.hotel.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
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
    private final StatutRepository statutRepository;
    private static final String UPLOAD_DIR="src/main/resources/static/objets/";

    @Transactional
    public ObjetDto create(ObjetRequest request) throws IOException {
        UUID Id = UUID.fromString(request.getId());

        ChambreChoisie chambreChoisie = chambreChoisieRepository.findById(Id).isPresent() ? chambreChoisieRepository.findById(Id).get() : null;
        SalleChoisie salleChoisie = salleChoisieRepository.findById(Id).isPresent() ? salleChoisieRepository.findById(Id).get() : null;

        /* Initialize Object and joined present entities ***/
        Objet objet = new Objet();
        objet.setChambreChoisie(chambreChoisie);
        objet.setSalleChoisie(salleChoisie);

        /* Join state to Object ***/
        objet.setStatut(statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found!")));

        /* Define the properties of the object ***/
        objet.setDescription(request.getDescription());

        /* Manage picture ****/
        String nomImage = saveImage(request.getImageBase64());
        objet.setPhoto(nomImage);

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
        Statut state = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldObjet.setStatut(state);
        oldObjet.setDescription(request.getDescription());
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

    private String saveImage(String imageBase64) throws IOException {
        if (imageBase64 == null || !imageBase64.contains(",")) {
            throw new IllegalArgumentException("Données d'image invalides ou non fournies.");
        }

        /* Separate metadata and image data ****/
        String[] splitted = imageBase64.split(",");
        String metadata = splitted[0];
        String base64Data = splitted[1];

        /* Check the MIME type of the image ****/
        String imageType = metadata.split(";")[0].split(":")[1];
        if (!imageType.startsWith("image/")) {
            throw new IllegalArgumentException("Le type de fichier n'est pas une image valide.");
        }

        /* Decode Base64 data *****/
        byte[] imageBytes;
        try {
            imageBytes = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Les données de l'image sont mal encodées en Base64.", e);
        }

        /* Manage a unique name for picture *****/
        String extension = imageType.split("/")[1];
        String nomImage = UUID.randomUUID() + "." + extension;

        /* Determine the path and save the image *****/
        Path path = Paths.get(UPLOAD_DIR, nomImage);
        Files.createDirectories(path.getParent()); // Create necessary repositories if non exist
        Files.write(path, imageBytes); // Write data in the file

        return nomImage;
    }


}
