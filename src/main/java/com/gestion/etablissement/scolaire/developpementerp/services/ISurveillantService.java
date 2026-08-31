package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SurveillantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SurveillantResponce;

import java.util.List;

public interface ISurveillantService {
    SurveillantResponce addSurveillant(SurveillantRequest surveillantRequest);
    SurveillantResponce updateSurveillant(Long idSurveillant, SurveillantRequest surveillantRequest);
    void deleteSurveillant(Long idSurveillant);
    List<SurveillantResponce> getAllSurveillants();
    SurveillantResponce getSurveillantById(Long idSurveillant);
}
