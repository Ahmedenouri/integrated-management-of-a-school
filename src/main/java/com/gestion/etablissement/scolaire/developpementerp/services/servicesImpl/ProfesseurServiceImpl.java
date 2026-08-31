package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ProfesseurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfesseurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Professeur;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IProfesseurMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ProfesseurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IProfesseurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProfesseurServiceImpl implements IProfesseurService {

    private final IProfesseurMapper professeurMapper;
    private final ProfesseurRepository professeurRepository;

    @Override
    public ProfesseurResponce addProfesseur(ProfesseurRequest professeurRequest) {
        log.debug("Adding professeur with email: {}", professeurRequest.getEmail());

        Professeur professeur = professeurMapper.map(professeurRequest);
        initializeDefaults(professeur);

        Professeur savedProfesseur = professeurRepository.save(professeur);
        return professeurMapper.map(savedProfesseur);
    }

    @Override
    public ProfesseurResponce updateProfesseur(Long idProfesseur, ProfesseurRequest professeurRequest) {
        log.debug("Updating professeur ID: {}", idProfesseur);

        Professeur existingProfesseur = findProfesseurOrThrow(idProfesseur);
        professeurMapper.updateFromRequest(professeurRequest, existingProfesseur);

        Professeur savedProfesseur = professeurRepository.save(existingProfesseur);
        return professeurMapper.map(savedProfesseur);
    }

    @Override
    public void deleteProfesseur(Long idProfesseur) {
        log.debug("Deleting professeur ID: {}", idProfesseur);

        if (!professeurRepository.existsById(idProfesseur)) {
            throw new ResourceNotFoundException("Professeur not found with ID: " + idProfesseur);
        }

        professeurRepository.deleteById(idProfesseur);
    }

    @Override
    public List<ProfesseurResponce> getAllProfesseurs() {
        log.debug("Fetching all professeurs");
        return professeurMapper.mapList(professeurRepository.findAll());
    }

    @Override
    public ProfesseurResponce getProfesseurById(Long idProfesseur) {
        log.debug("Fetching professeur ID: {}", idProfesseur);
        return professeurMapper.map(findProfesseurOrThrow(idProfesseur));
    }

    private Professeur findProfesseurOrThrow(Long idProfesseur) {
        return professeurRepository.findById(idProfesseur)
                .orElseThrow(() -> new ResourceNotFoundException("Professeur not found with ID: " + idProfesseur));
    }

    private void initializeDefaults(Professeur professeur) {
        if (professeur.getRole() == null) {
            professeur.setRole(Role.PROFESSEUR);
        }
        if (professeur.getDateCreation() == null) {
            professeur.setDateCreation(LocalDateTime.now());
        }
        if (professeur.getEstActif() == null) {
            professeur.setEstActif(true);
        }
    }
}
