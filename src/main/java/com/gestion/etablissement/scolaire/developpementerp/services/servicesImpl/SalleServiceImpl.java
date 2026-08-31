package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SalleRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SalleResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Salle;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.ISalleMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.SalleRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.ISalleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SalleServiceImpl implements ISalleService {

    private final ISalleMapper salleMapper;
    private final SalleRepository salleRepository;

    @Override
    public SalleResponce addSalle(SalleRequest salleRequest) {
        log.debug("Adding Salle: {}", salleRequest.getCodeSalle());

        Salle salle = salleMapper.map(salleRequest);
        Salle saved = salleRepository.save(salle);
        return salleMapper.map(saved);
    }

    @Override
    public SalleResponce updateSalle(Long idSalle, SalleRequest salleRequest) {
        log.debug("Updating Salle ID: {}", idSalle);

        Salle existing = findSalleOrThrow(idSalle);
        salleMapper.updateFromRequest(salleRequest, existing);

        Salle saved = salleRepository.save(existing);
        return salleMapper.map(saved);
    }

    @Override
    public void deleteSalle(Long idSalle) {
        log.debug("Deleting Salle ID: {}", idSalle);
        if (!salleRepository.existsById(idSalle)) {
            throw new ResourceNotFoundException("Salle not found with ID: " + idSalle);
        }
        salleRepository.deleteById(idSalle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalleResponce> getAllSalles() {
        log.debug("Fetching all Salles");
        return salleMapper.mapList(salleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public SalleResponce getSalleById(Long idSalle) {
        log.debug("Fetching Salle ID: {}", idSalle);
        return salleMapper.map(findSalleOrThrow(idSalle));
    }

    private Salle findSalleOrThrow(Long idSalle) {
        return salleRepository.findById(idSalle)
                .orElseThrow(() -> new ResourceNotFoundException("Salle not found with ID: " + idSalle));
    }
}
