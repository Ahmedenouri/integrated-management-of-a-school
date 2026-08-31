package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.JourSemaine;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeanceRequest {

    @NotNull(message = "Le jour de la semaine est obligatoire")
    private JourSemaine jour;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    @NotNull(message = "L'ID de l'emploi du temps est obligatoire")
    private Long emploiDuTempsId;

    private Long professeurId;
    private Long salleId;
    private Long matiereId;
    private Long surveillantId;
}
