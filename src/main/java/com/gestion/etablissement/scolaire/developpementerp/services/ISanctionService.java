package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SanctionRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SanctionResponce;

import java.util.List;

public interface ISanctionService {
    SanctionResponce addSanction(SanctionRequest sanctionRequest);
    SanctionResponce updateSanction(Long idSanction, SanctionRequest sanctionRequest);
    void deleteSanction(Long idSanction);
    List<SanctionResponce> getAllSanctions();
    SanctionResponce getSanctionById(Long idSanction);
    List<SanctionResponce> getSanctionsByEtudiantEmail(String email);
}
