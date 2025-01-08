package com.room.hotel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.room.hotel.model.Utilisateur;
import com.room.hotel.repository.UtilisateurRepository;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@SpringBootApplication
public class ControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlApplication.class, args);
	}

	@Bean
    public CommandLineRunner runner(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (!utilisateurRepository.findByEmail("administrateur@sra-hotel.com").isPresent()) {
                Utilisateur user = new Utilisateur();
                user.setEmail("administrateur@sra-hotel.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setNom("Administrateur");
                user.setRole("ADMIN");
                user.setConnected(false);
                utilisateurRepository.save(user);
            }
        };
    }

}
