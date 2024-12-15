package dsi.soutenance.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dsi.soutenance.model.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {

    Long deleteByDemandeurPhysiqueId(Long id);

    Long deleteByDemandeurMoraleId(Long id);

    List<Demande> findByCommuneProvinceRegionId(Long regionId);

    List<Demande> findByNumeroDemandeAndCodeDemande(Long numeroDemande, String codeDemande);

    List<Demande> findByStatut(String statut);

    @Query("SELECT d FROM Demande d WHERE d.statut = 'En cours de traitement'")
    List<Demande> findByIdAndStatutEnCoursDeTraitement(String statut);

    long countByStatut(String statut);

}
