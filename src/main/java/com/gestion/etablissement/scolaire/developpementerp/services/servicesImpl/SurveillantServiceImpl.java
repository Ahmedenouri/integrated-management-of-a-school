package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SurveillantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SurveillantResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Surveillant;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.ISurveillantMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SurveillantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.ISurveillantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SurveillantServiceImpl implements ISurveillantService {

    private final ISurveillantMapper surveillantMapper;
    private final SurveillantRepository surveillantRepository;

    @Override
    public SurveillantResponce addSurveillant(SurveillantRequest surveillantRequest) {
        log.debug("Adding surveillant with email: {}", surveillantRequest.getEmail());

        Surveillant surveillant = surveillantMapper.map(surveillantRequest);
        initializeDefaults(surveillant);

        Surveillant savedSurveillant = surveillantRepository.save(surveillant);
        return surveillantMapper.map(savedSurveillant);
    }

    @Override
    public SurveillantResponce updateSurveillant(Long idSurveillant, SurveillantRequest surveillantRequest) {
        log.debug("Updating surveillant ID: {}", idSurveillant);

        Surveillant existingSurveillant = findSurveillantOrThrow(idSurveillant);
        surveillantMapper.updateFromRequest(surveillantRequest, existingSurveillant);

        Surveillant savedSurveillant = surveillantRepository.save(existingSurveillant);
        return surveillantMapper.map(savedSurveillant);
    }

    @Override
    public void deleteSurveillant(Long idSurveillant) {
        log.debug("Deleting surveillant ID: {}", idSurveillant);

        if (!surveillantRepository.existsById(idSurveillant)) {
            throw new ResourceNotFoundException("Surveillant not found with ID: " + idSurveillant);
        }

        surveillantRepository.deleteById(idSurveillant);
    }

    @Override
    public List<SurveillantResponce> getAllSurveillants() {
        log.debug("Fetching all surveillants");
        return surveillantMapper.mapList(surveillantRepository.findAll());
    }

    @Override
    public SurveillantResponce getSurveillantById(Long idSurveillant) {
        log.debug("Fetching surveillant ID: {}", idSurveillant);
        return surveillantMapper.map(findSurveillantOrThrow(idSurveillant));
    }

    private Surveillant findSurveillantOrThrow(Long idSurveillant) {
        return surveillantRepository.findById(idSurveillant)
                .orElseThrow(() -> new ResourceNotFoundException("Surveillant not found with ID: " + idSurveillant));
    }

    private void initializeDefaults(Surveillant surveillant) {
        if (surveillant.getRole() == null) {
            surveillant.setRole(Role.SURVEILLANT);
        }
        if (surveillant.getDateCreation() == null) {
            surveillant.setDateCreation(LocalDateTime.now());
        }
        if (surveillant.getEstActif() == null) {
            surveillant.setEstActif(true);
        }
    }
}
