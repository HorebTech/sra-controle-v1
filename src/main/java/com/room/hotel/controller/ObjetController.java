package com.room.hotel.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room.hotel.dto.ObjetDto;
import com.room.hotel.request.ObjetRequest;
import com.room.hotel.service.ObjetService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/objet")
public class ObjetController {

    private final ObjetService service;

    @PostMapping("/create")
    public ResponseEntity<ObjetDto> create(@RequestBody @Valid ObjetRequest request) throws IOException {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ObjetDto>> getAll(){
        return ResponseEntity.ok(service.getAllObjet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getObjet(id));
    }

    @GetMapping("/{statut}/{numero}")
    public ResponseEntity<List<ObjetDto>> getObjetsByState(@PathVariable String statut, @PathVariable String numero){
        return ResponseEntity.ok(service.getObjetsByState(statut, numero));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ObjetDto> updateState(@PathVariable UUID id, @RequestBody ObjetRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateState(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjetDto> deleteObjet(@PathVariable UUID id) throws IOException {
        return ResponseEntity.ok(service.deleteObjet(id));
    }
}
