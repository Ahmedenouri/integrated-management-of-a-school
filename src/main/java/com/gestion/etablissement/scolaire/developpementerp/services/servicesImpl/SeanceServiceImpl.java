package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SeanceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SeanceResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Seance;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.ISeanceMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.*;
import com.gestion.etablissement.scolaire.developpementerp.services.ISeanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SeanceServiceImpl implements ISeanceService {

    private final ISeanceMapper seanceMapper;
    private final SeanceRepository seanceRepository;
    private final EmploiDuTempsRepository emploiDuTempsRepository;
    private final ProfesseurRepository professeurRepository;
    private final SalleRepository salleRepository;
    private final MatiereRepository matiereRepository;
    private final SurveillantRepository surveillantRepository;

    @Override
    public SeanceResponce addSeance(SeanceRequest seanceRequest) {
        log.debug("Adding Seance for emploi ID: {}", seanceRequest.getEmploiDuTempsId());

        Seance seance = seanceMapper.map(seanceRequest);
        assignRelations(seance, seanceRequest);

        Seance saved = seanceRepository.save(seance);
        return seanceMapper.map(saved);
    }

    @Override
    public SeanceResponce updateSeance(Long idSeance, SeanceRequest seanceRequest) {
        log.debug("Updating Seance ID: {}", idSeance);

        Seance existing = findSeanceOrThrow(idSeance);
        seanceMapper.updateFromRequest(seanceRequest, existing);
        assignRelations(existing, seanceRequest);

        Seance saved = seanceRepository.save(existing);
        return seanceMapper.map(saved);
    }

    @Override
    public void deleteSeance(Long idSeance) {
        log.debug("Deleting Seance ID: {}", idSeance);
        if (!seanceRepository.existsById(idSeance)) {
            throw new ResourceNotFoundException("Seance not found with ID: " + idSeance);
        }
        seanceRepository.deleteById(idSeance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeanceResponce> getAllSeances() {
        log.debug("Fetching all Seances");
        return seanceMapper.mapList(seanceRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public SeanceResponce getSeanceById(Long idSeance) {
        log.debug("Fetching Seance ID: {}", idSeance);
        return seanceMapper.map(findSeanceOrThrow(idSeance));
    }

    private Seance findSeanceOrThrow(Long idSeance) {
        return seanceRepository.findById(idSeance)
                .orElseThrow(() -> new ResourceNotFoundException("Seance not found with ID: " + idSeance));
    }

    private void assignRelations(Seance seance, SeanceRequest request) {
        if (request.getEmploiDuTempsId() != null) {
            seance.setEmploiDuTemps(emploiDuTempsRepository.findById(request.getEmploiDuTempsId())
                    .orElseThrow(() -> new ResourceNotFoundException("EmploiDuTemps not found with ID: " + request.getEmploiDuTempsId())));
        }
        if (request.getProfesseurId() != null) {
            seance.setProfesseur(professeurRepository.findById(request.getProfesseurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professeur not found with ID: " + request.getProfesseurId())));
        }
        if (request.getSalleId() != null) {
            seance.setSalle(salleRepository.findById(request.getSalleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Salle not found with ID: " + request.getSalleId())));
        }
        if (request.getMatiereId() != null) {
            seance.setMatiere(matiereRepository.findById(request.getMatiereId())
                    .orElseThrow(() -> new ResourceNotFoundException("Matiere not found with ID: " + request.getMatiereId())));
        }
        if (request.getSurveillantId() != null) {
            seance.setSurveillant(surveillantRepository.findById(request.getSurveillantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Surveillant not found with ID: " + request.getSurveillantId())));
        }
    }
}
