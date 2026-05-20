package com.sgaoa.entity;

import com.sgaoa.enums.Role;
import com.sgaoa.enums.StatutCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "adoptants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adoptant implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(unique = true)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.ADOPTANT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatutCompte statut = StatutCompte.EN_ATTENTE_VALIDATION;

    @Column(name = "email_verifie")
    @Builder.Default
    private Boolean emailVerifie = false;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "dernier_connexion")
    private LocalDateTime dernierConnexion;

    @Column(name = "token_verification_email")
    private String tokenVerificationEmail;

    @Column(name = "token_reinitialisation")
    private String tokenReinitialisation;

    @Column(name = "expiration_token_reinitialisation")
    private LocalDateTime expirationTokenReinitialisation;

    // Champs spécifiques à l'adoptant
    private String profession;
    private String adresse;
    private String situationFamiliale;
    private Integer nombreEnfants;
    @Column(name = "revenu_mensuel")
    private BigDecimal revenuMensuel;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return statut == StatutCompte.ACTIF;
    }
}
