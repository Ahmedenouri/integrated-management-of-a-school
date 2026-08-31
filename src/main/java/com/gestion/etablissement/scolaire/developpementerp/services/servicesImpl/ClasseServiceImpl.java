package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ClasseRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ClasseResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Classe;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IClasseMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ClasseRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IClasseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ClasseServiceImpl implements IClasseService {

    private final IClasseMapper classeMapper;
    private final ClasseRepository classeRepository;

    @Override
    public ClasseResponce addClasse(ClasseRequest classeRequest) {
        log.debug("Adding classe with nom: {}", classeRequest.getNom());
        Classe classe = classeMapper.map(classeRequest);
        Classe savedClasse = classeRepository.save(classe);
        return classeMapper.map(savedClasse);
    }

    @Override
    public ClasseResponce updateClasse(Long idClasse, ClasseRequest classeRequest) {
        log.debug("Updating classe ID: {}", idClasse);
        Classe existingClasse = findClasseOrThrow(idClasse);
        classeMapper.updateFromRequest(classeRequest, existingClasse);
        Classe savedClasse = classeRepository.save(existingClasse);
        return classeMapper.map(savedClasse);
    }

    @Override
    public void deleteClasse(Long idClasse) {
        log.debug("Deleting classe ID: {}", idClasse);
        if (!classeRepository.existsById(idClasse)) {
            throw new ResourceNotFoundException("Classe not found with ID: " + idClasse);
        }
        classeRepository.deleteById(idClasse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClasseResponce> getAllClasses() {
        log.debug("Fetching all classes");
        return classeMapper.mapList(classeRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ClasseResponce getClasseById(Long idClasse) {
        log.debug("Fetching classe ID: {}", idClasse);
        return classeMapper.map(findClasseOrThrow(idClasse));
    }

    private Classe findClasseOrThrow(Long idClasse) {
        return classeRepository.findById(idClasse)
                .orElseThrow(() -> new ResourceNotFoundException("Classe not found with ID: " + idClasse));
    }
}
