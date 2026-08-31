package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EvaluationRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EvaluationResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Evaluation;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IEvaluationMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EvaluationRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.MatiereRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IEvaluationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EvaluationServiceImpl implements IEvaluationService {

    private final IEvaluationMapper evaluationMapper;
    private final EvaluationRepository evaluationRepository;
    private final MatiereRepository matiereRepository;

    @Override
    public EvaluationResponce addEvaluation(EvaluationRequest evaluationRequest) {
        log.debug("Adding Evaluation: {}", evaluationRequest.getTitre());

        Evaluation evaluation = evaluationMapper.map(evaluationRequest);
        assignMatiere(evaluation, evaluationRequest.getMatiereId());

        Evaluation saved = evaluationRepository.save(evaluation);
        return evaluationMapper.map(saved);
    }

    @Override
    public EvaluationResponce updateEvaluation(Long idEvaluation, EvaluationRequest evaluationRequest) {
        log.debug("Updating Evaluation ID: {}", idEvaluation);

        Evaluation existing = findEvaluationOrThrow(idEvaluation);
        evaluationMapper.updateFromRequest(evaluationRequest, existing);
        assignMatiere(existing, evaluationRequest.getMatiereId());

        Evaluation saved = evaluationRepository.save(existing);
        return evaluationMapper.map(saved);
    }

    @Override
    public void deleteEvaluation(Long idEvaluation) {
        log.debug("Deleting Evaluation ID: {}", idEvaluation);
        if (!evaluationRepository.existsById(idEvaluation)) {
            throw new ResourceNotFoundException("Evaluation not found with ID: " + idEvaluation);
        }
        evaluationRepository.deleteById(idEvaluation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationResponce> getAllEvaluations() {
        log.debug("Fetching all Evaluations");
        return evaluationMapper.mapList(evaluationRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationResponce getEvaluationById(Long idEvaluation) {
        log.debug("Fetching Evaluation ID: {}", idEvaluation);
        return evaluationMapper.map(findEvaluationOrThrow(idEvaluation));
    }

    private Evaluation findEvaluationOrThrow(Long idEvaluation) {
        return evaluationRepository.findById(idEvaluation)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + idEvaluation));
    }

    private void assignMatiere(Evaluation evaluation, Long matiereId) {
        if (matiereId != null) {
            evaluation.setMatiere(matiereRepository.findById(matiereId)
                    .orElseThrow(() -> new ResourceNotFoundException("Matiere not found with ID: " + matiereId)));
        }
    }
}
