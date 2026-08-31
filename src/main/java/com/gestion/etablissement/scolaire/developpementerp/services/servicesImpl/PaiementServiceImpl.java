package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.PaiementRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.PaiementResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Paiement;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IPaiementMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.PaiementRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ResponsableFinancierRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IPaiementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementMapper paiementMapper;
    private final PaiementRepository paiementRepository;
    private final EtudiantRepository etudiantRepository;
    private final ResponsableFinancierRepository responsableFinancierRepository;

    @Override
    public PaiementResponce addPaiement(PaiementRequest paiementRequest) {
        log.debug("Adding Paiement ref: {}", paiementRequest.getReferencePaiement());

        Paiement paiement = paiementMapper.map(paiementRequest);
        assignRelations(paiement, paiementRequest.getEtudiantId(), paiementRequest.getResponsableFinancierId());

        Paiement saved = paiementRepository.save(paiement);
        return paiementMapper.map(saved);
    }

    @Override
    public PaiementResponce updatePaiement(Long idPaiement, PaiementRequest paiementRequest) {
        log.debug("Updating Paiement ID: {}", idPaiement);

        Paiement existing = findPaiementOrThrow(idPaiement);
        paiementMapper.updateFromRequest(paiementRequest, existing);
        assignRelations(existing, paiementRequest.getEtudiantId(), paiementRequest.getResponsableFinancierId());

        Paiement saved = paiementRepository.save(existing);
        return paiementMapper.map(saved);
    }

    @Override
    public void deletePaiement(Long idPaiement) {
        log.debug("Deleting Paiement ID: {}", idPaiement);
        if (!paiementRepository.existsById(idPaiement)) {
            throw new ResourceNotFoundException("Paiement not found with ID: " + idPaiement);
        }
        paiementRepository.deleteById(idPaiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementResponce> getAllPaiements() {
        log.debug("Fetching all Paiements");
        return paiementMapper.mapList(paiementRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementResponce getPaiementById(Long idPaiement) {
        log.debug("Fetching Paiement ID: {}", idPaiement);
        return paiementMapper.map(findPaiementOrThrow(idPaiement));
    }

    private Paiement findPaiementOrThrow(Long idPaiement) {
        return paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement not found with ID: " + idPaiement));
    }

    private void assignRelations(Paiement paiement, Long etudiantId, Long responsableFinancierId) {
        if (etudiantId != null) {
            paiement.setEtudiant(etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + etudiantId)));
        }
        if (responsableFinancierId != null) {
            paiement.setResponsableFinancier(responsableFinancierRepository.findById(responsableFinancierId)
                    .orElseThrow(() -> new ResourceNotFoundException("ResponsableFinancier not found with ID: " + responsableFinancierId)));
        }
    }
}
