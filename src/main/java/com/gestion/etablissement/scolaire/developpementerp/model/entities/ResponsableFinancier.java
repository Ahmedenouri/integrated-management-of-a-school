package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Setter
@Getter
public class ResponsableFinancier extends Utilisateur {
    // Comportement métier exposé dans le service
}
