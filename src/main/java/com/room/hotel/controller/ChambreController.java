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

import com.room.hotel.dto.ChambreDto;
import com.room.hotel.request.ChambreRequest;
import com.room.hotel.service.ChambreService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
@RequestMapping(ROOT_URL + "/chambre")
public class ChambreController {

    private final ChambreService service;

    @PostMapping("/create")
    public ResponseEntity<ChambreDto> create(@RequestBody @Valid ChambreRequest request) throws IOException {
        return ResponseEntity.ok(service.createChambre(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChambreDto> update(@PathVariable UUID id, @RequestBody ChambreRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateChambre(id, request));
    }

    @PutMapping("/update/{id}/{nom}")
    public ResponseEntity<ChambreDto> updateState(@PathVariable UUID id, @PathVariable String nom)
            throws IOException {
        return ResponseEntity.ok(service.updateChambreEtat(id, nom));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteChambre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ChambreDto>> getAllChambres(){
        return ResponseEntity.ok(service.getChambres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChambreDto> getChambre(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getChambre(id));
    }

    @GetMapping("/get-all/{nom}")
    public ResponseEntity<List<ChambreDto>> getAllChambresByState(@PathVariable String nom){
        return ResponseEntity.ok(service.getChambresByState(nom));
    }

}
