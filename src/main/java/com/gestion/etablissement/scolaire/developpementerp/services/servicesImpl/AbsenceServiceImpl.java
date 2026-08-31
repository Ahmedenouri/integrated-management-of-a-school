package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.AbsenceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.AbsenceResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Absence;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IAbsenceMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.AbsenceRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SeanceRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SurveillantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IAbsenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AbsenceServiceImpl implements IAbsenceService {

    private final IAbsenceMapper absenceMapper;
    private final AbsenceRepository absenceRepository;
    private final SeanceRepository seanceRepository;
    private final EtudiantRepository etudiantRepository;
    private final SurveillantRepository surveillantRepository;

    @Override
    public AbsenceResponce addAbsence(AbsenceRequest absenceRequest) {
        log.debug("Adding absence for etudiant ID: {}", absenceRequest.getEtudiantId());

        Absence absence = absenceMapper.map(absenceRequest);
        assignRelations(absence, absenceRequest.getSeanceId(), absenceRequest.getEtudiantId(), absenceRequest.getSurveillantId());

        Absence savedAbsence = absenceRepository.save(absence);
        return absenceMapper.map(savedAbsence);
    }

    @Override
    public AbsenceResponce updateAbsence(Long idAbsence, AbsenceRequest absenceRequest) {
        log.debug("Updating absence ID: {}", idAbsence);

        Absence existingAbsence = findAbsenceOrThrow(idAbsence);
        absenceMapper.updateFromRequest(absenceRequest, existingAbsence);
        assignRelations(existingAbsence, absenceRequest.getSeanceId(), absenceRequest.getEtudiantId(), absenceRequest.getSurveillantId());

        Absence savedAbsence = absenceRepository.save(existingAbsence);
        return absenceMapper.map(savedAbsence);
    }

    @Override
    public void deleteAbsence(Long idAbsence) {
        log.debug("Deleting absence ID: {}", idAbsence);
        if (!absenceRepository.existsById(idAbsence)) {
            throw new ResourceNotFoundException("Absence not found with ID: " + idAbsence);
        }
        absenceRepository.deleteById(idAbsence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceResponce> getAllAbsences() {
        log.debug("Fetching all absences");
        return absenceMapper.mapList(absenceRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AbsenceResponce getAbsenceById(Long idAbsence) {
        log.debug("Fetching absence ID: {}", idAbsence);
        return absenceMapper.map(findAbsenceOrThrow(idAbsence));
    }

    private Absence findAbsenceOrThrow(Long idAbsence) {
        return absenceRepository.findById(idAbsence)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found with ID: " + idAbsence));
    }

    private void assignRelations(Absence absence, Long seanceId, Long etudiantId, Long surveillantId) {
        if (seanceId != null) {
            absence.setSeance(seanceRepository.findById(seanceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Seance not found with ID: " + seanceId)));
        }
        if (etudiantId != null) {
            absence.setEtudiant(etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + etudiantId)));
        }
        if (surveillantId != null) {
            absence.setSurveillant(surveillantRepository.findById(surveillantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Surveillant not found with ID: " + surveillantId)));
        }
    }
}
