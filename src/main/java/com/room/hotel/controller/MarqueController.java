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

import com.room.hotel.dto.MarqueDto;
import com.room.hotel.service.MarqueService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/marque")
public class MarqueController {
    
    private final MarqueService marqueService;
    
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<MarqueDto> create(@RequestBody MarqueDto dto) throws IOException {
        return ResponseEntity.ok(marqueService.createMarque(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<MarqueDto> update(@PathVariable UUID id, @RequestBody MarqueDto marqueDto)
            throws IOException {
        return ResponseEntity.ok(marqueService.updateMarque(id, marqueDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        marqueService.deleteMarque(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<MarqueDto>> getAllMarques(){
        return ResponseEntity.ok(marqueService.findAllMarques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarqueDto> getMarque(@PathVariable UUID id) {
        return ResponseEntity.ok(marqueService.getMarque(id));
    }

}
