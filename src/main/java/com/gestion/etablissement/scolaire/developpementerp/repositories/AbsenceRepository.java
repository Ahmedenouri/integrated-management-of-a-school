package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    List<Absence> findByEtudiantId(Long etudiantId);

    @Query("SELECT COALESCE(SUM(a.nombreHeures), 0) FROM Absence a WHERE a.etudiant.id = :etudiantId")
    Integer sumNombreHeuresByEtudiantId(@Param("etudiantId") Long etudiantId);
}
