package com.room.hotel.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "passe")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Passe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Utilisateur agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gouvernante_id")
    private Utilisateur gouvernante;

    @ManyToOne
    @JoinColumn(name = "statut_id", nullable = false)
    private Statut statut;

    private String commentaire;
    
    private String dateNettoyage;

    @OneToMany(mappedBy = "passe", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    private List<ChambreChoisie> chambreChoisie;

    @OneToMany(mappedBy = "passe", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    private List<SalleChoisie> salleChoisie;

    @OneToMany(mappedBy = "passe", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    private List<TacheChoisie> tacheChoisie;

    @OneToMany(mappedBy = "passe", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    private List<NettoyageChoisie> nettoyageChoisie;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy ' à ' HH:mm:ss")
    private LocalDateTime creationDate;
    @LastModifiedDate
    @Column(insertable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy ' à ' HH:mm:ss")
    private LocalDateTime modificationDate;
}
