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

import com.room.hotel.dto.ChambreChoisieDto;
import com.room.hotel.request.ChambreChoisieRequest;
import com.room.hotel.service.ChambreChoisieService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/chambre-choisie")
public class ChambreChoisieController {

    private final ChambreChoisieService service;

    @PostMapping("/create")
    public ResponseEntity<ChambreChoisieDto> create(@RequestBody ChambreChoisieRequest request) throws IOException {
        return ResponseEntity.ok(service.createChambreChoisie(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ChambreChoisieDto>> getAllChambres(){
        return ResponseEntity.ok(service.getAllChambresChoisies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChambreChoisieDto> getChambreChoisie(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getChambreChoisie(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteChambreChoisie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChambreChoisieDto> updateChambreChoisieState(@PathVariable UUID id, @RequestBody ChambreChoisieRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateChambreChoisieState(id, request));
    }

}
