package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SalleRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SalleResponce;

import java.util.List;

public interface ISalleService {
    SalleResponce addSalle(SalleRequest salleRequest);
    SalleResponce updateSalle(Long idSalle, SalleRequest salleRequest);
    void deleteSalle(Long idSalle);
    List<SalleResponce> getAllSalles();
    SalleResponce getSalleById(Long idSalle);
}
