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

import com.room.hotel.dto.UtilisateurDto;
import com.room.hotel.request.LoginRequest;
import com.room.hotel.request.PasswordRequest;
import com.room.hotel.request.RefreshTokenRequest;
import com.room.hotel.request.RegisterRequest;
import com.room.hotel.service.UtilisateurService;
import static com.room.hotel.utils.ApiUrls.ROOT_URL;
import com.room.hotel.utils.AuthResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT_URL)
public class UtilisateurController {

    private final UtilisateurService userService;

    @PostMapping("/admin/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws IOException {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    /***Récupérer tous les utilisateurs******/
    @GetMapping("/admin/get-all/users")
    public ResponseEntity<List<UtilisateurDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping("/admin/delete/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/{nom}")
    public ResponseEntity<AuthResponse> loginName(@PathVariable String nom) throws IOException {
        return ResponseEntity.ok(userService.getUserByName(nom));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws IOException {
        return ResponseEntity.ok(userService.loginUser(request));
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) throws IOException {
        return ResponseEntity.ok(userService.refreshToken(request));
    }

    @PostMapping("/users/logout/{email}")
    public ResponseEntity<AuthResponse> logout(@PathVariable String email) throws IOException {
        return ResponseEntity.ok(userService.logoutUser(email));
    }

    @PutMapping("/users/update-user/{userId}")
    public ResponseEntity<UtilisateurDto> updateUser(@PathVariable UUID userId, @RequestBody UtilisateurDto utilisateurDto) throws IOException{
        return ResponseEntity.ok(userService.updateUser(userId, utilisateurDto));
    }

    @GetMapping("/auth/{userId}")
    public ResponseEntity<UtilisateurDto> getUser(@PathVariable UUID userId) throws IOException{
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/auth/change-password")
    public ResponseEntity<UtilisateurDto> changePassword(@Valid @RequestBody PasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }

}
