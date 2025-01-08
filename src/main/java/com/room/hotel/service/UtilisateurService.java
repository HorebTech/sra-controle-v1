package com.room.hotel.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.room.hotel.dto.UtilisateurDto;
import com.room.hotel.mapper.UtilisateurMapper;
import com.room.hotel.request.PasswordRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.room.hotel.exception.ResourceNotFoundException;
import com.room.hotel.model.Utilisateur;
import com.room.hotel.repository.UtilisateurRepository;
import com.room.hotel.request.LoginRequest;
import com.room.hotel.request.RefreshTokenRequest;
import com.room.hotel.request.RegisterRequest;
import com.room.hotel.utils.AuthResponse;

import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UtilisateurMapper utilisateurMapper;

    public AuthResponse getUserByName(String nom) {
        AuthResponse authResponse = new AuthResponse();
        Utilisateur utilisateur = utilisateurRepository.findByNom(nom)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur inconnu!"));
        authResponse.setEmail(utilisateur.getEmail());
        authResponse.setNom(utilisateur.getNom());
        authResponse.setRole(utilisateur.getRole());
        authResponse.setId(utilisateur.getId());
        authResponse.setPhoto(utilisateur.getPhoto());
        authResponse.setConnected(utilisateur.getConnected());
        authResponse.setStatusCode(200);
        return authResponse;
    }

    public AuthResponse registerUser(RegisterRequest registerRequest) throws IOException {
        AuthResponse authResponse = new AuthResponse();

        if (utilisateurRepository.findByNom(registerRequest.getNom()).isPresent()) {
            throw new IllegalStateException("Ce nom est déjà utilisé!");
        }
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            utilisateur.setEmail(registerRequest.getEmail());
            utilisateur.setNom(registerRequest.getNom());
            utilisateur.setConnected(false);
            utilisateur.setRole(registerRequest.getRole());
            Utilisateur utilisateurResultat = utilisateurRepository.save(utilisateur);

            if(utilisateurResultat.getId() != null) {
                authResponse.setUtilisateur(utilisateurResultat);
                authResponse.setMessage("Nouvel utilisateur enrégistré.");
                authResponse.setStatusCode(200);
            }
        } catch (JwtException | IllegalArgumentException e) {
            authResponse.setError(e.getMessage());
            authResponse.setStatusCode(500);
        }
        return authResponse;
    }

    public AuthResponse loginUser(LoginRequest request) {
        AuthResponse authResponse = new AuthResponse();

        Utilisateur username = utilisateurRepository.findByNom(request.getNom())
        .orElseThrow(() -> new ResourceNotFoundException("Le nom est requis!"));

        if(!Objects.equals(request.getEmail(), username.getEmail())) {
            throw new ResourceNotFoundException("Email invalide!");
        }
        try {
            var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var claims = new HashMap<String, Object>();
            var utilisateur = ((Utilisateur) auth.getPrincipal());
            claims.put("role", utilisateur.getRole());
            var accessToken = jwtService.generateToken(claims, (Utilisateur) auth.getPrincipal());
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), utilisateur);
            utilisateur.setConnected(true);
            utilisateurRepository.save(utilisateur);

            authResponse.setStatusCode(200);
            authResponse.setNom(utilisateur.getNom());
            authResponse.setRole(utilisateur.getRole());
            authResponse.setEmail(utilisateur.getEmail());
            authResponse.setAccessToken(accessToken);
            authResponse.setRefreshToken(refreshToken);
            authResponse.setExpirationTime("24Hr");
            authResponse.setMessage("Connexion réussit.");
        } catch (JwtException | IllegalArgumentException e) {
            authResponse.setStatusCode(500);
            authResponse.setError(e.getMessage());
        }
        return authResponse;
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        //System.out.println(request);
        AuthResponse authResponse = new AuthResponse();
        String userEmail = jwtService.extractUsername(request.getRefreshToken());
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userEmail).orElseThrow();
        var claims = new HashMap<String, Object>();
        claims.put("role", utilisateur.getRole());
        if(jwtService.isTokenValid(request.getRefreshToken(), utilisateur)) {
            var jwt = jwtService.generateToken(claims, utilisateur);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), utilisateur);
            authResponse.setNom(utilisateur.getNom());
            authResponse.setRole(utilisateur.getRole());
            authResponse.setEmail(utilisateur.getEmail());
            authResponse.setAccessToken(jwt);
            authResponse.setRefreshToken(refreshToken);
            authResponse.setExpirationTime("24Hr");
            authResponse.setMessage("Refresh token réussit.");
        }
        authResponse.setStatusCode(500);
        return authResponse;
    }

    public AuthResponse logoutUser(String email) {
        AuthResponse authResponse = new AuthResponse();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow();
        try {
            utilisateur.setConnected(false);
            utilisateurRepository.save(utilisateur);
            authResponse.setMessage("Déconnexion réussit.");
            authResponse.setStatusCode(200);
        } catch (JwtException | IllegalArgumentException e) {
            authResponse.setError(e.getMessage());
            authResponse.setStatusCode(500);
        }
        return authResponse;
    }

    public UtilisateurDto updateUser(UUID userId, UtilisateurDto utilisateurDto) {
        Utilisateur oldUser = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé!"));
        if(utilisateurDto.getPassword() != null){
            oldUser.setPassword(passwordEncoder.encode(utilisateurDto.getPassword()));
        }
        if(!Objects.equals(oldUser.getNom(), utilisateurDto.getNom()) && utilisateurRepository.findByNom(utilisateurDto.getNom()).isPresent()){
            throw new ResourceNotFoundException("Nom déjà utilisé!");
        }
        if(utilisateurDto.getPhoto() != null){
            oldUser.setPhoto(utilisateurDto.getPhoto());
        }

        oldUser.setEmail(utilisateurDto.getEmail());
        oldUser.setNom(utilisateurDto.getNom());
        if(utilisateurDto.getRole() != null) {
            oldUser.setRole(utilisateurDto.getRole());
        }
        oldUser.setConnected(oldUser.getConnected());
        Utilisateur userUpdated = utilisateurRepository.save(oldUser);
        return utilisateurMapper.toDto(userUpdated);
    }

    public UtilisateurDto getUser(UUID userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé!"));
        return utilisateurMapper.toDto(utilisateur);
    }

    public UtilisateurDto changePassword(PasswordRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé!"));
        utilisateur.setPassword(passwordEncoder.encode(request.getNewPassword()));
        utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateur);
    }

    /***Récupérer tous les utilisateurs*/
    public List<UtilisateurDto> findAllUsers() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return utilisateurs.stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }

        /**** Supprimer une salle ****/
    @Transactional
    public void deleteUser(UUID id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé.");
        }
        utilisateurRepository.deleteById(id);
    }
}
