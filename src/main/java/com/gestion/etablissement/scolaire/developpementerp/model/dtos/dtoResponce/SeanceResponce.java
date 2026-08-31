package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.JourSemaine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeanceResponce {

    private Long id;
    private JourSemaine jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Long emploiDuTempsId;
    private Long professeurId;
    private Long salleId;
    private Long matiereId;
    private Long surveillantId;
}
