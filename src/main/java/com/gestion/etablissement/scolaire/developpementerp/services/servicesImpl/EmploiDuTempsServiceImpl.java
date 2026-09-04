package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EmploiDuTempsRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EmploiDuTempsResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.EmploiDuTemps;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IEmploiDuTempsMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ClasseRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EmploiDuTempsRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SurveillantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IEmploiDuTempsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class EmploiDuTempsServiceImpl implements IEmploiDuTempsService {

    private final IEmploiDuTempsMapper emploiDuTempsMapper;
    private final EmploiDuTempsRepository emploiDuTempsRepository;
    private final ClasseRepository classeRepository;
    private final SurveillantRepository surveillantRepository;

    @Override
    public EmploiDuTempsResponce addEmploiDuTemps(EmploiDuTempsRequest emploiDuTempsRequest) {
        log.debug("Adding EmploiDuTemps for classe ID: {}", emploiDuTempsRequest.getClasseId());

        EmploiDuTemps emploiDuTemps = emploiDuTempsMapper.map(emploiDuTempsRequest);
        if (emploiDuTemps.getEstValide() == null) {
            emploiDuTemps.setEstValide(false);
        }
        assignRelations(emploiDuTemps, emploiDuTempsRequest.getClasseId(), emploiDuTempsRequest.getSurveillantId());

        EmploiDuTemps saved = emploiDuTempsRepository.save(emploiDuTemps);
        return emploiDuTempsMapper.map(saved);
    }

    @Override
    public EmploiDuTempsResponce updateEmploiDuTemps(Long idEmploi, EmploiDuTempsRequest emploiDuTempsRequest) {
        log.debug("Updating EmploiDuTemps ID: {}", idEmploi);

        EmploiDuTemps existing = findEmploiOrThrow(idEmploi);
        emploiDuTempsMapper.updateFromRequest(emploiDuTempsRequest, existing);
        assignRelations(existing, emploiDuTempsRequest.getClasseId(), emploiDuTempsRequest.getSurveillantId());

        EmploiDuTemps saved = emploiDuTempsRepository.save(existing);
        return emploiDuTempsMapper.map(saved);
    }

    @Override
    public void deleteEmploiDuTemps(Long idEmploi) {
        log.debug("Deleting EmploiDuTemps ID: {}", idEmploi);
        if (!emploiDuTempsRepository.existsById(idEmploi)) {
            throw new ResourceNotFoundException("EmploiDuTemps not found with ID: " + idEmploi);
        }
        emploiDuTempsRepository.deleteById(idEmploi);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmploiDuTempsResponce> getAllEmploisDuTemps() {
        log.debug("Fetching all EmploisDuTemps");
        return emploiDuTempsMapper.mapList(emploiDuTempsRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EmploiDuTempsResponce getEmploiDuTempsById(Long idEmploi) {
        log.debug("Fetching EmploiDuTemps ID: {}", idEmploi);
        return emploiDuTempsMapper.map(findEmploiOrThrow(idEmploi));
    }

    @Override
    @Transactional(readOnly = true)
    public EmploiDuTempsResponce getEmploiForEtudiant(String email) {
        log.debug("Fetching EmploiDuTemps for etudiant: {}", email);
        List<EmploiDuTemps> emplois = emploiDuTempsRepository.findByClasseEtudiantsEmail(email);
        if (emplois.isEmpty()) {
            throw new ResourceNotFoundException("Aucun emploi du temps trouvé pour l'étudiant avec l'email: " + email);
        }
        return emploiDuTempsMapper.map(emplois.get(0));
    }

    private EmploiDuTemps findEmploiOrThrow(Long idEmploi) {
        return emploiDuTempsRepository.findById(idEmploi)
                .orElseThrow(() -> new ResourceNotFoundException("EmploiDuTemps not found with ID: " + idEmploi));
    }

    private void assignRelations(EmploiDuTemps emploiDuTemps, Long classeId, Long surveillantId) {
        if (classeId != null) {
            emploiDuTemps.setClasse(classeRepository.findById(classeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Classe not found with ID: " + classeId)));
        }
        if (surveillantId != null) {
            emploiDuTemps.setSurveillant(surveillantRepository.findById(surveillantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Surveillant not found with ID: " + surveillantId)));
        }
    }
}
