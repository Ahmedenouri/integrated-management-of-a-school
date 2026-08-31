package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EvaluationRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EvaluationResponce;

import java.util.List;

public interface IEvaluationService {
    EvaluationResponce addEvaluation(EvaluationRequest evaluationRequest);
    EvaluationResponce updateEvaluation(Long idEvaluation, EvaluationRequest evaluationRequest);
    void deleteEvaluation(Long idEvaluation);
    List<EvaluationResponce> getAllEvaluations();
    EvaluationResponce getEvaluationById(Long idEvaluation);
}
