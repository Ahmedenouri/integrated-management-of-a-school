package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.RecuRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.RecuResponce;

import java.util.List;

public interface IRecuService {
    RecuResponce addRecu(RecuRequest recuRequest);
    RecuResponce updateRecu(Long idRecu, RecuRequest recuRequest);
    void deleteRecu(Long idRecu);
    List<RecuResponce> getAllRecus();
    RecuResponce getRecuById(Long idRecu);
    List<RecuResponce> getRecusByEtudiantEmail(String email);
}
