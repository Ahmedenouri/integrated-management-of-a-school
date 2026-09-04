package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardResponce;

public interface IDashboardService {
    DashboardResponce getDashboardStats();
    com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardFinancierResponse getDashboardFinancierStats();
    com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardDisciplineResponse getDashboardDisciplineStats();
}
