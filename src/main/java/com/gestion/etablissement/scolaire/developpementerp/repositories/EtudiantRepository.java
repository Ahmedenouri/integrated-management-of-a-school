package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByEmail(String email);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT e FROM Etudiant e WHERE e.classe.id IN (SELECT s.emploiDuTemps.classe.id FROM Seance s WHERE s.professeur.email = :profEmail)")
    List<Etudiant> findEtudiantsByProfesseurEmail(@org.springframework.data.repository.query.Param("profEmail") String profEmail);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(e) > 0 FROM Etudiant e WHERE e.id = :etudiantId AND e.classe.id IN (SELECT s.emploiDuTemps.classe.id FROM Seance s WHERE s.professeur.email = :profEmail)")
    boolean isProfesseurOfEtudiant(@org.springframework.data.repository.query.Param("profEmail") String profEmail, @org.springframework.data.repository.query.Param("etudiantId") Long etudiantId);
}
