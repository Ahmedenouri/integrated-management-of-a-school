package com.gestion.etablissement.scolaire.developpementerp.repositories;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Paiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByStatutIn(List<StatutPaiement> statuts);

    List<Paiement> findByEtudiantId(Long etudiantId);

    List<Paiement> findByEtudiantEmail(String email);

    @Query("SELECT COALESCE(SUM(p.montant), 0.0) FROM Paiement p WHERE p.statut = :statut")
    Double sumMontantByStatut(@Param("statut") StatutPaiement statut);

    @Query("SELECT COALESCE(SUM(p.montant), 0.0) FROM Paiement p WHERE p.statut IN :statuts")
    Double sumMontantByStatutIn(@Param("statuts") List<StatutPaiement> statuts);

    @Query("SELECT COUNT(DISTINCT p.etudiant.id) FROM Paiement p WHERE p.statut IN :statuts")
    Long countDistinctEtudiantByStatutIn(@Param("statuts") List<StatutPaiement> statuts);
}
