package com.room.hotel.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.room.hotel.model.Utilisateur;

public class ApplicationAuditAware implements AuditorAware<UUID> {
    @SuppressWarnings("null")
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        Utilisateur userPrincipal = (Utilisateur) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getId());
    }
}