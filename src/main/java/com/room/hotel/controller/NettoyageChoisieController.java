package com.room.hotel.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room.hotel.dto.NettoyageChoisieDto;
import com.room.hotel.request.NettoyageChoisieRequest;
import com.room.hotel.service.NettoyageChoisieService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/nettoyage-choisie")
public class NettoyageChoisieController {

    private final NettoyageChoisieService service;

    @PostMapping("/create")
    public ResponseEntity<NettoyageChoisieDto> create(@RequestBody NettoyageChoisieRequest request) throws IOException {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<NettoyageChoisieDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NettoyageChoisieDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
