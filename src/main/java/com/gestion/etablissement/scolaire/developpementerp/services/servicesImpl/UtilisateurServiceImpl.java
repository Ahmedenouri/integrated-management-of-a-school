package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.UtilisateurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IUtilisateurMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.UtilisateurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IUtilisateurService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
@Slf4j
public class UtilisateurServiceImpl implements IUtilisateurService {
    private final UtilisateurRepository  utilisateurRepository;
    private final IUtilisateurMapper utilisateurMapper;


    @Override
    public List<UtilisateurResponce> getAllUtilisateurs() {
        log.debug("getAllUtilisateurs SERVICE");
        return utilisateurMapper.maptolistUtilisateur(utilisateurRepository.findAll());
    }
}
