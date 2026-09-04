package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SanctionRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SanctionResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Sanction;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.ISanctionMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SanctionRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SurveillantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.ISanctionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SanctionServiceImpl implements ISanctionService {

    private final ISanctionMapper sanctionMapper;
    private final SanctionRepository sanctionRepository;
    private final EtudiantRepository etudiantRepository;
    private final SurveillantRepository surveillantRepository;

    @Override
    public SanctionResponce addSanction(SanctionRequest sanctionRequest) {
        log.debug("Adding Sanction for etudiant ID: {}", sanctionRequest.getEtudiantId());

        Sanction sanction = sanctionMapper.map(sanctionRequest);
        if (sanction.getEstTraitee() == null) {
            sanction.setEstTraitee(false);
        }
        assignRelations(sanction, sanctionRequest.getEtudiantId(), sanctionRequest.getSurveillantId());

        Sanction saved = sanctionRepository.save(sanction);
        return sanctionMapper.map(saved);
    }

    @Override
    public SanctionResponce updateSanction(Long idSanction, SanctionRequest sanctionRequest) {
        log.debug("Updating Sanction ID: {}", idSanction);

        Sanction existing = findSanctionOrThrow(idSanction);
        sanctionMapper.updateFromRequest(sanctionRequest, existing);
        assignRelations(existing, sanctionRequest.getEtudiantId(), sanctionRequest.getSurveillantId());

        Sanction saved = sanctionRepository.save(existing);
        return sanctionMapper.map(saved);
    }

    @Override
    public void deleteSanction(Long idSanction) {
        log.debug("Deleting Sanction ID: {}", idSanction);
        if (!sanctionRepository.existsById(idSanction)) {
            throw new ResourceNotFoundException("Sanction not found with ID: " + idSanction);
        }
        sanctionRepository.deleteById(idSanction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SanctionResponce> getAllSanctions() {
        log.debug("Fetching all Sanctions");
        return sanctionMapper.mapList(sanctionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public SanctionResponce getSanctionById(Long idSanction) {
        log.debug("Fetching Sanction ID: {}", idSanction);
        return sanctionMapper.map(findSanctionOrThrow(idSanction));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SanctionResponce> getSanctionsByEtudiantEmail(String email) {
        log.debug("Fetching sanctions for etudiant email: {}", email);
        return sanctionMapper.mapList(sanctionRepository.findByEtudiantEmail(email));
    }

    private Sanction findSanctionOrThrow(Long idSanction) {
        return sanctionRepository.findById(idSanction)
                .orElseThrow(() -> new ResourceNotFoundException("Sanction not found with ID: " + idSanction));
    }

    private void assignRelations(Sanction sanction, Long etudiantId, Long surveillantId) {
        if (etudiantId != null) {
            sanction.setEtudiant(etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + etudiantId)));
        }
        if (surveillantId != null) {
            sanction.setSurveillant(surveillantRepository.findById(surveillantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Surveillant not found with ID: " + surveillantId)));
        }
    }
}
