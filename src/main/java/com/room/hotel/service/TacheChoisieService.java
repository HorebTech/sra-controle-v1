package com.room.hotel.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.TacheChoisieDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.TacheChoisieMapper;
import com.room.hotel.model.Passe;
import com.room.hotel.model.Tache;
import com.room.hotel.model.TacheChoisie;
import com.room.hotel.repository.PasseRepository;
import com.room.hotel.repository.TacheChoisieRepository;
import com.room.hotel.repository.TacheRepository;
import com.room.hotel.request.TacheChoisieRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TacheChoisieService {

    private final TacheChoisieRepository repository;
    private final TacheRepository tacheRepository;
    private final PasseRepository passeRepository;
    private final TacheChoisieMapper mapper;

    @Transactional
    public TacheChoisieDto create(TacheChoisieRequest request) throws IOException {
        Passe passe = passeRepository.findById(request.getPasseId()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        Tache tache = tacheRepository.findByAction(request.getAction()).orElseThrow(() -> new ResourceNotFoundException("Oups, une erreur est survenue!"));
        TacheChoisie tacheChoisie = new TacheChoisie();
        tacheChoisie.setPasse(passe);
        tacheChoisie.setTache(tache);
        TacheChoisie savedTacheChoisie = repository.save(tacheChoisie);
        return mapper.toDto(savedTacheChoisie);
    }

    public TacheChoisieDto getOne(UUID id) {
        TacheChoisie tacheChoisie = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("erreur!"));
        return mapper.toDto(tacheChoisie);
    }

    public List<TacheChoisieDto> getAll() {
        List<TacheChoisie> TacheChoisie = repository.findAll();
        return TacheChoisie.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune tache trouvée");
        }
        repository.deleteById(id);
    }
}
