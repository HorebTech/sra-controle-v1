package com.room.hotel.service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.room.hotel.dto.MarqueDto;
import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.mapper.MarqueMapper;
import com.room.hotel.model.Marque;
import com.room.hotel.repository.MarqueRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarqueService {
    private final MarqueRepository repository;
    private final MarqueMapper mapper;

    @Transactional
    public MarqueDto createMarque(MarqueDto dto) throws IOException {
        Marque marque = mapper.toEntity(dto);
        Marque savedMarque = repository.save(marque);
        return mapper.toDto(savedMarque);
    }

    @Transactional
    public void deleteMarque(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Aucune Marque trouvée!");
        }
        repository.deleteById(id);
    }

    public MarqueDto getMarque(UUID id) {
        Marque Marque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marque non trouvée!"));
        return mapper.toDto(Marque);
    }

    /**** Modifier une marque ****/
    @Transactional
    public MarqueDto updateMarque(UUID id, MarqueDto marqueDto) throws IOException {
        Marque oldMarque = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune marque trouvée!"));
        oldMarque.setNom(marqueDto.getNom());
        Marque updatedMarque = repository.save(oldMarque);
        return mapper.toDto(updatedMarque);
    }

    public List<MarqueDto> findAllMarques() {
        List<Marque> marques = repository.findAll();
        return marques.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
