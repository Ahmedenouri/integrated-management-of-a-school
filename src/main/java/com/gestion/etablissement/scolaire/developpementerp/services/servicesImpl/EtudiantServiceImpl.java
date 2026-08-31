package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EtudiantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EtudiantResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Classe;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IEtudiantMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ClasseRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IEtudiantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService {

    private final IEtudiantMapper etudiantMapper;
    private final EtudiantRepository etudiantRepository;
    private final ClasseRepository classeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EtudiantResponce addEtudiant(EtudiantRequest etudiantRequest) {
        log.debug("Adding etudiant with email: {}", etudiantRequest.getEmail());

        Etudiant etudiant = etudiantMapper.map(etudiantRequest);
        if (etudiant.getMotDePasse() != null && !etudiant.getMotDePasse().startsWith("$2a$")) {
            etudiant.setMotDePasse(passwordEncoder.encode(etudiant.getMotDePasse()));
        }
        initializeDefaults(etudiant);
        assignClasse(etudiant, etudiantRequest.getClasseId());

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        return etudiantMapper.map(savedEtudiant);
    }

    @Override
    public EtudiantResponce updateEtudiant(Long idEtudiant, EtudiantRequest etudiantRequest) {
        log.debug("Updating etudiant ID: {}", idEtudiant);

        Etudiant existingEtudiant = findEtudiantOrThrow(idEtudiant);
        etudiantMapper.updateFromRequest(etudiantRequest, existingEtudiant);
        if (etudiantRequest.getMotDePasse() != null && !etudiantRequest.getMotDePasse().isEmpty() && !etudiantRequest.getMotDePasse().startsWith("$2a$")) {
            existingEtudiant.setMotDePasse(passwordEncoder.encode(etudiantRequest.getMotDePasse()));
        }

        if (etudiantRequest.getClasseId() != null) {
            assignClasse(existingEtudiant, etudiantRequest.getClasseId());
        }

        Etudiant savedEtudiant = etudiantRepository.save(existingEtudiant);
        return etudiantMapper.map(savedEtudiant);
    }

    @Override
    public void deleteEtudiant(Long idEtudiant) {
        log.debug("Deleting etudiant ID: {}", idEtudiant);

        if (!etudiantRepository.existsById(idEtudiant)) {
            throw new ResourceNotFoundException("Etudiant not found with ID: " + idEtudiant);
        }

        etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public List<EtudiantResponce> getAllEtudiants() {
        log.debug("Fetching all etudiants");
        return etudiantMapper.mapList(etudiantRepository.findAll());
    }

    @Override
    public EtudiantResponce getEtudiantById(Long idEtudiant) {
        log.debug("Fetching etudiant ID: {}", idEtudiant);
        return etudiantMapper.map(findEtudiantOrThrow(idEtudiant));
    }

    private Etudiant findEtudiantOrThrow(Long idEtudiant) {
        return etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + idEtudiant));
    }

    private void initializeDefaults(Etudiant etudiant) {
        if (etudiant.getRole() == null) {
            etudiant.setRole(Role.ETUDIANT);
        }
        if (etudiant.getDateCreation() == null) {
            etudiant.setDateCreation(LocalDateTime.now());
        }
        if (etudiant.getEstActif() == null) {
            etudiant.setEstActif(true);
        }
    }

    private void assignClasse(Etudiant etudiant, Long classeId) {
        if (classeId == null) {
            return;
        }

        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new ResourceNotFoundException("Classe not found with ID: " + classeId));
        etudiant.setClasse(classe);
    }
}
