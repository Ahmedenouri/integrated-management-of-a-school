package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Directeur extends Utilisateur {
    // Comportement métier exposé dans le service — pas de champs supplémentaires pour l'instant
}
