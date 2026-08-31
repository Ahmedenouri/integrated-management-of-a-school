package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.DirecteurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DirecteurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Directeur;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IDirecteurMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.DirecteurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IDirecteurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DirecteurServiceImpl implements IDirecteurService {

    private final IDirecteurMapper directeurMapper;
    private final DirecteurRepository directeurRepository;

    @Override
    public DirecteurResponce addDirecteur(DirecteurRequest directeurRequest) {
        log.debug("Adding directeur with email: {}", directeurRequest.getEmail());

        Directeur directeur = directeurMapper.map(directeurRequest);
        if (directeur.getRole() == null) {
            directeur.setRole(Role.DIRECTEUR);
        }
        if (directeur.getDateCreation() == null) {
            directeur.setDateCreation(LocalDateTime.now());
        }
        if (directeur.getEstActif() == null) {
            directeur.setEstActif(true);
        }

        Directeur savedDirecteur = directeurRepository.save(directeur);
        return directeurMapper.map(savedDirecteur);
    }

    @Override
    public DirecteurResponce updateDirecteur(Long idDirecteur, DirecteurRequest directeurRequest) {
        log.debug("Updating directeur ID: {}", idDirecteur);

        Directeur existingDirecteur = findDirecteurOrThrow(idDirecteur);
        directeurMapper.updateFromRequest(directeurRequest, existingDirecteur);

        Directeur savedDirecteur = directeurRepository.save(existingDirecteur);
        return directeurMapper.map(savedDirecteur);
    }

    @Override
    public void deleteDirecteur(Long idDirecteur) {
        log.debug("Deleting directeur ID: {}", idDirecteur);

        if (!directeurRepository.existsById(idDirecteur)) {
            throw new ResourceNotFoundException("Directeur not found with ID: " + idDirecteur);
        }

        directeurRepository.deleteById(idDirecteur);
    }

    @Override
    public List<DirecteurResponce> getAllDirecteurs() {
        log.debug("Fetching all directeurs");
        return directeurMapper.mapList(directeurRepository.findAll());
    }

    @Override
    public DirecteurResponce getDirecteurById(Long idDirecteur) {
        log.debug("Fetching directeur ID: {}", idDirecteur);
        return directeurMapper.map(findDirecteurOrThrow(idDirecteur));
    }

    private Directeur findDirecteurOrThrow(Long idDirecteur) {
        return directeurRepository.findById(idDirecteur)
                .orElseThrow(() -> new ResourceNotFoundException("Directeur not found with ID: " + idDirecteur));
    }
}
