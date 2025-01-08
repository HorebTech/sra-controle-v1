package com.room.hotel.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.room.hotel.dto.PanneDto;
import com.room.hotel.request.PanneRequest;
import com.room.hotel.service.PanneService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL + "/panne")
public class PanneController {

    private final PanneService service;

    @PostMapping("/create")
    public ResponseEntity<PanneDto> create(@RequestBody PanneRequest request) throws IOException {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PanneDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get-all/{creationDateString}")
    public ResponseEntity<List<PanneDto>> getByDay(@PathVariable String creationDateString){
        return ResponseEntity.ok(service.getByDay(creationDateString));
    }

    @GetMapping("/{statut}/{numero}")
    public ResponseEntity<List<PanneDto>> getPannesByState(@PathVariable String statut, @PathVariable String numero){
        return ResponseEntity.ok(service.getPannesByState(statut, numero));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<PanneDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PanneDto> updateState(@PathVariable UUID id, @RequestBody PanneRequest request)
            throws IOException {
        return ResponseEntity.ok(service.updateState(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PanneDto> delete(@PathVariable UUID id) throws IOException {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("/get-all/{dateDebut}/{dateFin}")
    public ResponseEntity<Object> getBetweenDates(
            @PathVariable String dateDebut,
            @PathVariable String dateFin
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateDebutLocalDateTime = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime dateFinLocalDateTime = LocalDateTime.parse(dateFin, formatter);
        return ResponseEntity.ok(service.findBetweenDates(dateDebutLocalDateTime, dateFinLocalDateTime));
    }

    @GetMapping("/get-all/room/{dateDebut}/{dateFin}")
    public ResponseEntity<Object> findRoomBetweenDates(
            @PathVariable String dateDebut,
            @PathVariable String dateFin
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateDebutLocalDateTime = LocalDateTime.parse(dateDebut, formatter);
        LocalDateTime dateFinLocalDateTime = LocalDateTime.parse(dateFin, formatter);
        return ResponseEntity.ok(service.getByRoomBetweenDates(dateDebutLocalDateTime, dateFinLocalDateTime));
    }

}
