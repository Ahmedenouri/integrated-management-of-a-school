package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.RecuRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.RecuResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Recu;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IRecuMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.PaiementRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.RecuRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IRecuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RecuServiceImpl implements IRecuService {

    private final IRecuMapper recuMapper;
    private final RecuRepository recuRepository;
    private final PaiementRepository paiementRepository;

    @Override
    public RecuResponce addRecu(RecuRequest recuRequest) {
        log.debug("Adding Recu: {}", recuRequest.getNumeroRecu());

        Recu recu = recuMapper.map(recuRequest);
        assignPaiement(recu, recuRequest.getPaiementId());

        Recu saved = recuRepository.save(recu);
        return recuMapper.map(saved);
    }

    @Override
    public RecuResponce updateRecu(Long idRecu, RecuRequest recuRequest) {
        log.debug("Updating Recu ID: {}", idRecu);

        Recu existing = findRecuOrThrow(idRecu);
        recuMapper.updateFromRequest(recuRequest, existing);
        assignPaiement(existing, recuRequest.getPaiementId());

        Recu saved = recuRepository.save(existing);
        return recuMapper.map(saved);
    }

    @Override
    public void deleteRecu(Long idRecu) {
        log.debug("Deleting Recu ID: {}", idRecu);
        if (!recuRepository.existsById(idRecu)) {
            throw new ResourceNotFoundException("Recu not found with ID: " + idRecu);
        }
        recuRepository.deleteById(idRecu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecuResponce> getAllRecus() {
        log.debug("Fetching all Recus");
        return recuMapper.mapList(recuRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RecuResponce getRecuById(Long idRecu) {
        log.debug("Fetching Recu ID: {}", idRecu);
        return recuMapper.map(findRecuOrThrow(idRecu));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecuResponce> getRecusByEtudiantEmail(String email) {
        log.debug("Fetching Recus for etudiant email: {}", email);
        return recuMapper.mapList(recuRepository.findByPaiementEtudiantEmail(email));
    }

    private Recu findRecuOrThrow(Long idRecu) {
        return recuRepository.findById(idRecu)
                .orElseThrow(() -> new ResourceNotFoundException("Recu not found with ID: " + idRecu));
    }

    private void assignPaiement(Recu recu, Long paiementId) {
        if (paiementId != null) {
            recu.setPaiement(paiementRepository.findById(paiementId)
                    .orElseThrow(() -> new ResourceNotFoundException("Paiement not found with ID: " + paiementId)));
        }
    }
}
