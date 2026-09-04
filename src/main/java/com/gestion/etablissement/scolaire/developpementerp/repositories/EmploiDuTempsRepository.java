package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.EmploiDuTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploiDuTempsRepository extends JpaRepository<EmploiDuTemps,Long> {

    java.util.List<EmploiDuTemps> findByClasseId(Long classeId);

    java.util.List<EmploiDuTemps> findByClasseEtudiantsEmail(String email);
}
