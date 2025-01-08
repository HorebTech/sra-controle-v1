package com.room.hotel.controller;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room.hotel.dto.SalleDto;
import com.room.hotel.request.SalleRequest;
import com.room.hotel.service.SalleService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
@RequestMapping(ROOT_URL + "/salle")
public class SalleController {

    private final SalleService service;

    @PostMapping("/create")
    public ResponseEntity<SalleDto> create(@RequestBody @Valid SalleRequest request) throws IOException {
        return ResponseEntity.ok(service.createSalle(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SalleDto> update(@PathVariable UUID id, @RequestBody SalleRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateSalle(id, request));
    }

    @PutMapping("/update/{id}/{nom}")
    public ResponseEntity<SalleDto> updateState(@PathVariable UUID id, @PathVariable String nom)
            throws IOException {
        return ResponseEntity.ok(service.updateSalleEtat(id, nom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SalleDto>> getAllSalles(){
        return ResponseEntity.ok(service.getSalles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalleDto> getSalle(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSalle(id));
    }

    @GetMapping("/get-all/{nom}")
    public ResponseEntity<List<SalleDto>> getAllSallesByState(@PathVariable String nom){
        return ResponseEntity.ok(service.getSallesByState(nom));
    }
}
