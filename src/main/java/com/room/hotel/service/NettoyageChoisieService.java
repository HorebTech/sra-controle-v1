package com.room.hotel.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.NettoyageChoisieDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.NettoyageChoisieMapper;
import com.room.hotel.model.Nettoyage;
import com.room.hotel.model.NettoyageChoisie;
import com.room.hotel.model.Passe;
import com.room.hotel.repository.NettoyageChoisieRepository;
import com.room.hotel.repository.NettoyageRepository;
import com.room.hotel.repository.PasseRepository;
import com.room.hotel.request.NettoyageChoisieRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NettoyageChoisieService {

    private final NettoyageChoisieRepository repository;
    private final NettoyageRepository nettoyageRepository;
    private final PasseRepository passeRepository;
    private final NettoyageChoisieMapper mapper;

    @Transactional
    public NettoyageChoisieDto create(NettoyageChoisieRequest request) throws IOException {
        
        Passe passe = passeRepository.findById(request.getPasseId()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        Nettoyage Nettoyage = nettoyageRepository.findByAction(request.getAction()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        NettoyageChoisie nettoyageChoisie = new NettoyageChoisie();
        nettoyageChoisie.setPasse(passe);
        nettoyageChoisie.setNettoyage(Nettoyage);
        NettoyageChoisie savedNettoyageChoisie = repository.save(nettoyageChoisie);
        return mapper.toDto(savedNettoyageChoisie);
    }

    public NettoyageChoisieDto getOne(UUID id) {
        NettoyageChoisie NettoyageChoisie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("erreur!"));
        return mapper.toDto(NettoyageChoisie);
    }

    public List<NettoyageChoisieDto> getAll() {
        List<NettoyageChoisie> NettoyageChoisie = repository.findAll();
        return NettoyageChoisie.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucun nettoyage trouvé");
        }
        repository.deleteById(id);
    }
}
