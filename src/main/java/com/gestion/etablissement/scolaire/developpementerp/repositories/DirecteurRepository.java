package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Directeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirecteurRepository extends JpaRepository<Directeur, Long> {
    //List<Directeur> findByDepartement(String departement);
}
