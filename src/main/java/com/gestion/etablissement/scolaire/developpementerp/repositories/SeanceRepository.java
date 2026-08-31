package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Seance;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.JourSemaine;
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
                                                 @Param("heureFin") LocalTime heureFin,
                                                 @Param("currentId") Long currentId);
}
