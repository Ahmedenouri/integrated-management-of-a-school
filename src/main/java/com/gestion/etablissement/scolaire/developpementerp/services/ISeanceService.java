package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SeanceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SeanceResponce;

import java.util.List;

public interface ISeanceService {
    SeanceResponce addSeance(SeanceRequest seanceRequest);
    SeanceResponce updateSeance(Long idSeance, SeanceRequest seanceRequest);
    void deleteSeance(Long idSeance);
    List<SeanceResponce> getAllSeances();
    SeanceResponce getSeanceById(Long idSeance);
}
