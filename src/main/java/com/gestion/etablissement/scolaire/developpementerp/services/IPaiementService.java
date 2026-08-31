package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.PaiementRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.PaiementResponce;

import java.util.List;

public interface IPaiementService {
    PaiementResponce addPaiement(PaiementRequest paiementRequest);
    PaiementResponce updatePaiement(Long idPaiement, PaiementRequest paiementRequest);
    void deletePaiement(Long idPaiement);
    List<PaiementResponce> getAllPaiements();
    PaiementResponce getPaiementById(Long idPaiement);
}
