package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.MatiereRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.MatiereResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Matiere;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IMatiereMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.MatiereRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IMatiereService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MatiereServiceImpl implements IMatiereService {

    private final IMatiereMapper matiereMapper;
    private final MatiereRepository matiereRepository;

    @Override
    public MatiereResponce addMatiere(MatiereRequest matiereRequest) {
        log.debug("Adding Matiere: {}", matiereRequest.getIntitule());

        Matiere matiere = matiereMapper.map(matiereRequest);
        Matiere saved = matiereRepository.save(matiere);
        return matiereMapper.map(saved);
    }

    @Override
    public MatiereResponce updateMatiere(Long idMatiere, MatiereRequest matiereRequest) {
        log.debug("Updating Matiere ID: {}", idMatiere);

        Matiere existing = findMatiereOrThrow(idMatiere);
        matiereMapper.updateFromRequest(matiereRequest, existing);

        Matiere saved = matiereRepository.save(existing);
        return matiereMapper.map(saved);
    }

    @Override
    public void deleteMatiere(Long idMatiere) {
        log.debug("Deleting Matiere ID: {}", idMatiere);
        if (!matiereRepository.existsById(idMatiere)) {
            throw new ResourceNotFoundException("Matiere not found with ID: " + idMatiere);
        }
        matiereRepository.deleteById(idMatiere);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatiereResponce> getAllMatieres() {
        log.debug("Fetching all Matieres");
        return matiereMapper.mapList(matiereRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public MatiereResponce getMatiereById(Long idMatiere) {
        log.debug("Fetching Matiere ID: {}", idMatiere);
        return matiereMapper.map(findMatiereOrThrow(idMatiere));
    }

    private Matiere findMatiereOrThrow(Long idMatiere) {
        return matiereRepository.findById(idMatiere)
                .orElseThrow(() -> new ResourceNotFoundException("Matiere not found with ID: " + idMatiere));
    }
}
