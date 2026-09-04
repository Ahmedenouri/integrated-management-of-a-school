package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Seance;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.JourSemaine;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    @Query("SELECT s FROM Seance s WHERE s.jour = :jour AND s.salle.id = :salleId AND s.heureDebut < :heureFin AND s.heureFin > :heureDebut AND (:currentId IS NULL OR s.id != :currentId)")
    List<Seance> findConflictingSalleSeances(@Param("jour") JourSemaine jour,
                                            @Param("salleId") Long salleId,
                                            @Param("heureDebut") LocalTime heureDebut,
                                            @Param("heureFin") LocalTime heureFin,
                                            @Param("currentId") Long currentId);

    @Query("SELECT s FROM Seance s WHERE s.jour = :jour AND s.professeur.id = :professeurId AND s.heureDebut < :heureFin AND s.heureFin > :heureDebut AND (:currentId IS NULL OR s.id != :currentId)")
    List<Seance> findConflictingProfesseurSeances(@Param("jour") JourSemaine jour,
                                                  @Param("professeurId") Long professeurId,
                                                  @Param("heureDebut") LocalTime heureDebut,
                                                  @NotNull(message = "L'heure de fin est obligatoire") LocalTime heureFin, @Param("currentId") Long currentId);

    List<Seance> findByProfesseurEmail(String email);

    List<Seance> findByEmploiDuTempsClasseEtudiantsEmail(String email);

    List<Seance> findByEmploiDuTempsClasseId(Long classeId);

    @Query("SELECT COUNT(s) > 0 FROM Seance s WHERE s.professeur.email = :email AND s.matiere.id = :matiereId")
    boolean existsByProfesseurEmailAndMatiereId(@Param("email") String email, @Param("matiereId") Long matiereId);

    @Query("SELECT COUNT(s) > 0 FROM Seance s WHERE s.professeur.email = :email AND s.emploiDuTemps.classe.id = :classeId")
    boolean existsByProfesseurEmailAndClasseId(@Param("email") String email, @Param("classeId") Long classeId);

    @Query("SELECT DISTINCT s.emploiDuTemps.classe FROM Seance s WHERE s.professeur.email = :email")
    List<com.gestion.etablissement.scolaire.developpementerp.model.entities.Classe> findDistinctClassesByProfesseurEmail(@Param("email") String email);
}
