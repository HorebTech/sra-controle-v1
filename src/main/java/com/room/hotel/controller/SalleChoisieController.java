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

import com.room.hotel.dto.SalleChoisieDto;
import com.room.hotel.request.SalleChoisieRequest;
import com.room.hotel.service.SalleChoisieService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/salle-choisie")
public class SalleChoisieController {

    private final SalleChoisieService service;

    @PostMapping("/create")
    public ResponseEntity<SalleChoisieDto> create(@RequestBody SalleChoisieRequest request) throws IOException {
        return ResponseEntity.ok(service.createSalleChoisie(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SalleChoisieDto>> getAllSalles(){
        return ResponseEntity.ok(service.getAllSallesChoisies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalleChoisieDto> getSalleChoisie(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSalleChoisie(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteSalleChoisie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalleChoisieDto> updateState(@PathVariable UUID id, @RequestBody SalleChoisieRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateState(id, request));
    }

}
