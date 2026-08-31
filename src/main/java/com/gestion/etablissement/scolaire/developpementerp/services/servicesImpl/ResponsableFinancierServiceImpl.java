package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ResponsableFinancierRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ResponsableFinancierResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.ResponsableFinancier;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IResponsableFinancierMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ResponsableFinancierRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IResponsableFinancierService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ResponsableFinancierServiceImpl implements IResponsableFinancierService {

    private final IResponsableFinancierMapper responsableFinancierMapper;
    private final ResponsableFinancierRepository responsableFinancierRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponsableFinancierResponce addResponsableFinancier(ResponsableFinancierRequest responsableFinancierRequest) {
        log.debug("Adding responsable financier with email: {}", responsableFinancierRequest.getEmail());

        ResponsableFinancier responsableFinancier = responsableFinancierMapper.map(responsableFinancierRequest);
        if (responsableFinancier.getMotDePasse() != null && !responsableFinancier.getMotDePasse().startsWith("$2a$")) {
            responsableFinancier.setMotDePasse(passwordEncoder.encode(responsableFinancier.getMotDePasse()));
        }
        if (responsableFinancier.getRole() == null) {
            responsableFinancier.setRole(Role.RESPONSABLE_FINANCIER);
        }
        if (responsableFinancier.getDateCreation() == null) {
            responsableFinancier.setDateCreation(LocalDateTime.now());
        }
        if (responsableFinancier.getEstActif() == null) {
            responsableFinancier.setEstActif(true);
        }

        ResponsableFinancier savedResponsableFinancier = responsableFinancierRepository.save(responsableFinancier);
        return responsableFinancierMapper.map(savedResponsableFinancier);
    }

    @Override
    public ResponsableFinancierResponce updateResponsableFinancier(Long idResponsableFinancier, ResponsableFinancierRequest responsableFinancierRequest) {
        log.debug("Updating responsable financier ID: {}", idResponsableFinancier);

        ResponsableFinancier existingResponsableFinancier = findResponsableFinancierOrThrow(idResponsableFinancier);
        responsableFinancierMapper.updateFromRequest(responsableFinancierRequest, existingResponsableFinancier);
        if (responsableFinancierRequest.getMotDePasse() != null && !responsableFinancierRequest.getMotDePasse().isEmpty() && !responsableFinancierRequest.getMotDePasse().startsWith("$2a$")) {
            existingResponsableFinancier.setMotDePasse(passwordEncoder.encode(responsableFinancierRequest.getMotDePasse()));
        }

        ResponsableFinancier savedResponsableFinancier = responsableFinancierRepository.save(existingResponsableFinancier);
        return responsableFinancierMapper.map(savedResponsableFinancier);
    }

    @Override
    public void deleteResponsableFinancier(Long idResponsableFinancier) {
        log.debug("Deleting responsable financier ID: {}", idResponsableFinancier);

        if (!responsableFinancierRepository.existsById(idResponsableFinancier)) {
            throw new ResourceNotFoundException("ResponsableFinancier not found with ID: " + idResponsableFinancier);
        }

        responsableFinancierRepository.deleteById(idResponsableFinancier);
    }


    @Override
    public List<ResponsableFinancierResponce> getAllResponsablesFinanciers() {
        log.debug("Fetching all responsable financiers");
        return responsableFinancierMapper.mapList(responsableFinancierRepository.findAll());
    }

    @Override
    public ResponsableFinancierResponce getResponsableFinancierById(Long idResponsableFinancier) {
        log.debug("Fetching responsable financier ID: {}", idResponsableFinancier);
        return responsableFinancierMapper.map(findResponsableFinancierOrThrow(idResponsableFinancier));
    }

    private ResponsableFinancier findResponsableFinancierOrThrow(Long idResponsableFinancier) {
        return responsableFinancierRepository.findById(idResponsableFinancier)
                .orElseThrow(() -> new ResourceNotFoundException("ResponsableFinancier not found with ID: " + idResponsableFinancier));
    }
}
