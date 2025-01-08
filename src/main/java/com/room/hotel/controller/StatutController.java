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

import com.room.hotel.dto.StatutDto;
import com.room.hotel.service.StatutService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
@RequestMapping(ROOT_URL + "/statut")
public class StatutController {

    private final StatutService statutService;

    @PostMapping("/create")
    public ResponseEntity<StatutDto> create(@RequestBody StatutDto dto) throws IOException {
        return ResponseEntity.ok(statutService.createStatut(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatutDto> update(@PathVariable UUID id, @RequestBody StatutDto statutDto)
            throws IOException {
        return ResponseEntity.ok(statutService.updateStatut(id, statutDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        statutService.deleteStatut(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StatutDto>> getAllStatuts(){
        return ResponseEntity.ok(statutService.findAllStatuts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatutDto> getStatut(@PathVariable UUID id) {
        return ResponseEntity.ok(statutService.getStatut(id));
    }

}
