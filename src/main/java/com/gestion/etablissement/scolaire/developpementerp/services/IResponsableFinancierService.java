package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ResponsableFinancierRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ResponsableFinancierResponce;

import java.util.List;

public interface IResponsableFinancierService {
    ResponsableFinancierResponce addResponsableFinancier(ResponsableFinancierRequest responsableFinancierRequest);
    ResponsableFinancierResponce updateResponsableFinancier(Long idResponsableFinancier, ResponsableFinancierRequest responsableFinancierRequest);
    void deleteResponsableFinancier(Long idResponsableFinancier);
    List<ResponsableFinancierResponce> getAllResponsablesFinanciers();
    ResponsableFinancierResponce getResponsableFinancierById(Long idResponsableFinancier);
}
