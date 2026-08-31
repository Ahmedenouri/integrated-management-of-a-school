package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Sanction;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeSanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Long> {

    List<Sanction> findByEtudiantId(Long etudiantId);

    boolean existsByEtudiantIdAndType(Long etudiantId, TypeSanction type);
}
