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

import com.room.hotel.dto.CategorieDto;
import com.room.hotel.service.CategorieService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_GOUVERNANTE','ROLE_MANAGER', 'ROLE_ADMIN')")
@RequestMapping(ROOT_URL + "/categorie")
public class CategorieController {

    private final CategorieService categorieService;

    @PostMapping("/create")
    public ResponseEntity<CategorieDto> create(@RequestBody CategorieDto dto) throws IOException {
        return ResponseEntity.ok(categorieService.createCategorie(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDto> update(@PathVariable UUID id, @RequestBody CategorieDto categorieDto)
            throws IOException {
        return ResponseEntity.ok(categorieService.updateCategorie(id, categorieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategorieDto>> getAllCategories(){
        return ResponseEntity.ok(categorieService.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDto> getCategorie(@PathVariable UUID id) {
        return ResponseEntity.ok(categorieService.getCategorie(id));
    }

}
