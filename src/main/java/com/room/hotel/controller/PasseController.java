package com.room.hotel.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

import com.room.hotel.dto.PasseDto;
import com.room.hotel.request.PasseRequest;
import com.room.hotel.service.PasseService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/passe")
public class PasseController {
    
    private final PasseService service;
    
    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<PasseDto> createPasse(@RequestBody @Valid PasseRequest request, Principal connectedUser) throws IOException {
        return ResponseEntity.ok(service.createPasse(request, connectedUser));
    }

    @PutMapping("/update/{id}/{nom}")
    public ResponseEntity<PasseDto> updateState(@PathVariable Long id, @PathVariable String nom)
            throws IOException {
        return ResponseEntity.ok(service.updateState(id, nom));
    }

    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PasseDto> updatePasse(@PathVariable Long id, @RequestBody PasseRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updatePasse(id, request));
    }

    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletePasse(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER','ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<PasseDto>> getAllPasses() {
        return ResponseEntity.ok(service.findAllPasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasseDto> getPasse(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPasse(id));
    }

    @GetMapping("/get-all/{nom}")
    public ResponseEntity<List<PasseDto>> getPasseByState(
            @PathVariable String nom) {
        return ResponseEntity.ok(service.getPasseByState(nom));
    }

    @GetMapping("/get-all/{nom}/{agent}")
    public ResponseEntity<List<PasseDto>> getByStateAndName(@PathVariable String nom, @PathVariable String agent) {
        return ResponseEntity.ok(service.getByStateAndName(nom, agent));
    }

    @GetMapping("/get-all/{nom}/{agent}/{date}")
    public ResponseEntity<List<PasseDto>> getByStateAndNameAndDate(@PathVariable String nom, @PathVariable String agent, @PathVariable String date) {
        return ResponseEntity.ok(service.getByStateAndNameAndDate(nom, agent, date));
    }

    @GetMapping("/get-all/agent/{agent}/{date}")
    public ResponseEntity<List<PasseDto>> getNewAndCurrent(@PathVariable String agent, @PathVariable String date) {
        return ResponseEntity.ok(service.getNewAndCurrent(agent, date));
    }
}
