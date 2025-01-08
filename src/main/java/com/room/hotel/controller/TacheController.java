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

import com.room.hotel.dto.TacheDto;
import com.room.hotel.request.TacheRequest;
import com.room.hotel.service.TacheService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/tache")
public class TacheController {

    private final TacheService service;

    @PostMapping("/create")
    public ResponseEntity<TacheDto> create(@RequestBody TacheRequest request) throws IOException {
        return ResponseEntity.ok(service.saveTache(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheDto> update(@PathVariable UUID id, @RequestBody TacheRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateTache(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteTache(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TacheDto>> getFullTache(){
        return ResponseEntity.ok(service.getAllTache());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheDto> getTache(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getTache(id));
    }

}
