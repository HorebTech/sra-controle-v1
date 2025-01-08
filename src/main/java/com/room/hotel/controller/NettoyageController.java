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

import com.room.hotel.dto.NettoyageDto;
import com.room.hotel.request.NettoyageRequest;
import com.room.hotel.service.NettoyageService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER','ROLE_ADMIN')")
@RequestMapping(ROOT_URL + "/nettoyage")
public class NettoyageController {

    private final NettoyageService nettoyageService;

    @PostMapping("/create")
    public ResponseEntity<NettoyageDto> create(@RequestBody NettoyageRequest request) throws IOException {
        return ResponseEntity.ok(nettoyageService.createNettoyage(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NettoyageDto> update(@PathVariable UUID id, @RequestBody NettoyageRequest request)
            throws IOException {
        return ResponseEntity.ok(nettoyageService.updateNettoyage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        nettoyageService.deleteNettoyage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<NettoyageDto>> getAllNettoyages(){
        return ResponseEntity.ok(nettoyageService.findAllNettoyages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NettoyageDto> getNettoyage(@PathVariable UUID id) {
        return ResponseEntity.ok(nettoyageService.getNettoyage(id));
    }

}
