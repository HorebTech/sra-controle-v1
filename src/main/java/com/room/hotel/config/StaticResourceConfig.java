package com.room.hotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapping pour les fichiers statiques
        registry.addResourceHandler("/uploads/**") // URL publique
                .addResourceLocations("file:./uploads/") // Chemin absolu vers ton dossier
                .setCachePeriod(3600); // Cache les ressources pendant 1 heure
    }
}
