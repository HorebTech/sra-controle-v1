package com.room.hotel.service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.room.hotel.dto.PasseDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.PasseMapper;
import com.room.hotel.model.Passe;
import com.room.hotel.model.Statut;
import com.room.hotel.model.Utilisateur;
import com.room.hotel.repository.PasseRepository;
import com.room.hotel.repository.StatutRepository;
import com.room.hotel.repository.UtilisateurRepository;
import com.room.hotel.request.PasseRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasseService {

    private final PasseRepository repository;
    private final UtilisateurRepository utilisateurRepository;
    private final StatutRepository statutRepository;
    private final PasseMapper mapper;

    @Transactional
    public PasseDto createPasse(PasseRequest request, Principal connectedUser) throws IOException {
        Passe passe = new Passe();
        Utilisateur utilisateur = utilisateurRepository.findByNom(request.getAgent()).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé!"));
        var userConnected = (Utilisateur)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Utilisateur gouvernante = utilisateurRepository.findById(userConnected.getId()).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé!"));
        
        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));

        if(request.getCommentaire() != null && !request.getCommentaire().isEmpty()){
            passe.setCommentaire(request.getCommentaire());
        }
        passe.setGouvernante(gouvernante);
        passe.setAgent(utilisateur);
        passe.setDateNettoyage(request.getDateNettoyage());
        passe.setStatut(statut);
        Passe savedPasse = repository.save(passe);
        return mapper.toDto(savedPasse);
    }

    @Transactional
    public void deletePasse(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucun Passe trouvé!");
        }
        repository.deleteById(id);
    }

    public PasseDto getPasse(Long id) {
        Passe passe = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passe non trouvé!"));
    Hibernate.initialize(passe.getAgent());
    Hibernate.initialize(passe.getGouvernante());
        return mapper.toDto(passe);
    }

    public List<PasseDto> getPasseByState(String nom) {
        List<Passe> passes = repository.getByState(nom);
        return passes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PasseDto> getByStateAndName(String nom, String agent) {
        List<Passe> passes = repository.getByStateAndName(nom, agent);
        return passes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PasseDto> getByStateAndNameAndDate(String nom, String agent, String date) {
        List<Passe> passes = repository.getByStateAndNameAndDate(nom, agent, date);
        return passes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PasseDto> getNewAndCurrent(String agent, String date) {
        List<Passe> passes = repository.getNewAndCurrent(agent, date);
        return passes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PasseDto updateState(Long id, String nom) throws IOException {
        Passe oldPasse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun passe trouvé!"));
        Statut statut = statutRepository.findByNom(nom)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        oldPasse.setStatut(statut);
        Passe updatedPasse = repository.save(oldPasse);
        return mapper.toDto(updatedPasse);
    }

    /**** Modifier un passe ****/
    @Transactional
    public PasseDto updatePasse(Long id, PasseRequest request) throws IOException {
        Passe oldPasse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucun passe trouvé!"));
        Utilisateur utilisateur = utilisateurRepository.findByNom(request.getAgent()).orElseThrow(()-> new ResourceNotFoundException("Utilisateur non trouvé!"));
        Statut statut = statutRepository.findByNom(request.getStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Aucun statut trouvé!"));
        if(!request.getCommentaire().isEmpty()){
            oldPasse.setCommentaire(request.getCommentaire());
        }
        oldPasse.setAgent(utilisateur);
        oldPasse.setDateNettoyage(request.getDateNettoyage());
        oldPasse.setStatut(statut);
        Passe updatedPasse = repository.save(oldPasse);
        return mapper.toDto(updatedPasse);
    }

    public List<PasseDto> findAllPasses() {
        List<Passe> passes = repository.findAll();
        return passes.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
