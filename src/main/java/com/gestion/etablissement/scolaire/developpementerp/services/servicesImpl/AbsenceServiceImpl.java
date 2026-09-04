package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.AbsenceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.AbsenceResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Absence;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Sanction;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeSanction;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IAbsenceMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.AbsenceRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SanctionRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SeanceRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SurveillantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IAbsenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final SanctionRepository sanctionRepository;

    @Override
    public AbsenceResponce addAbsence(AbsenceRequest absenceRequest) {
        log.debug("Adding absence for etudiant ID: {}", absenceRequest.getEtudiantId());

        Absence absence = absenceMapper.map(absenceRequest);
        assignRelations(absence, absenceRequest.getSeanceId(), absenceRequest.getEtudiantId(), absenceRequest.getSurveillantId());

        Absence savedAbsence = absenceRepository.save(absence);
        checkAndTriggerSanctions(savedAbsence.getEtudiant());

        return absenceMapper.map(savedAbsence);
    }

    @Override
    public AbsenceResponce updateAbsence(Long idAbsence, AbsenceRequest absenceRequest) {
        log.debug("Updating absence ID: {}", idAbsence);

        Absence existingAbsence = findAbsenceOrThrow(idAbsence);
        absenceMapper.updateFromRequest(absenceRequest, existingAbsence);
        assignRelations(existingAbsence, absenceRequest.getSeanceId(), absenceRequest.getEtudiantId(), absenceRequest.getSurveillantId());

        Absence savedAbsence = absenceRepository.save(existingAbsence);
        checkAndTriggerSanctions(savedAbsence.getEtudiant());

        return absenceMapper.map(savedAbsence);
    }

    @Override
    public void deleteAbsence(Long idAbsence) {
        log.debug("Deleting absence ID: {}", idAbsence);
        Absence absence = findAbsenceOrThrow(idAbsence);
        Etudiant etudiant = absence.getEtudiant();
        absenceRepository.deleteById(idAbsence);
        if (etudiant != null) {
            log.debug("Recalculating absences after deletion for etudiant ID: {}", etudiant.getId());
        }
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

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceResponce> getAbsencesByEtudiantEmail(String email) {
        log.debug("Fetching absences for etudiant email: {}", email);
        return absenceMapper.mapList(absenceRepository.findByEtudiantEmail(email));
    }

    private Absence findAbsenceOrThrow(Long idAbsence) {
        return absenceRepository.findById(idAbsence)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found with ID: " + idAbsence));
    }

    private void checkAndTriggerSanctions(Etudiant etudiant) {
        if (etudiant == null || etudiant.getId() == null) return;

        Integer totalHours = absenceRepository.sumNombreHeuresByEtudiantId(etudiant.getId());
        log.info("Total cumulative absence hours for etudiant ID {}: {}h", etudiant.getId(), totalHours);

        if (totalHours > 20 && !sanctionRepository.existsByEtudiantIdAndType(etudiant.getId(), TypeSanction.CONVOCATION_PARENTS)) {
            log.warn("Triggering CONVOCATION_PARENTS sanction for etudiant ID: {}", etudiant.getId());
            Sanction sanction = new Sanction();
            sanction.setDateEmission(LocalDate.now());
            sanction.setType(TypeSanction.CONVOCATION_PARENTS);
            sanction.setMotif("Dépassement du seuil de 20 heures d'absences (" + totalHours + "h cumulées).");
            sanction.setTotalAbsencesAuMoment(totalHours);
            sanction.setEstTraitee(false);
            sanction.setEtudiant(etudiant);
            sanctionRepository.save(sanction);
        }

        if (totalHours > 30 && !sanctionRepository.existsByEtudiantIdAndType(etudiant.getId(), TypeSanction.CONSEIL_DISCIPLINE)) {
            log.warn("Triggering CONSEIL_DISCIPLINE sanction for etudiant ID: {}", etudiant.getId());
            Sanction sanction = new Sanction();
            sanction.setDateEmission(LocalDate.now());
            sanction.setType(TypeSanction.CONSEIL_DISCIPLINE);
            sanction.setMotif("Dépassement critique du seuil de 30 heures d'absences (" + totalHours + "h cumulées).");
            sanction.setTotalAbsencesAuMoment(totalHours);
            sanction.setEstTraitee(false);
            sanction.setEtudiant(etudiant);
            sanctionRepository.save(sanction);
        }
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
