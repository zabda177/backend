package projet_soutenance.dsi.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.DashboardStats;
import projet_soutenance.dsi.model.Demande;
import projet_soutenance.dsi.model.StatusDemande;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Demande, Long> {

    // Compter les demandes par statut
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = :statut")
    long countByStatut(@Param("statut") String statut);

    // Trouver les demandes par statut
    List<Demande> findByStatut(String statut);


    /**
     * Compter les demandes par statut et type de demande
     * @param statut Le statut de la demande
     * @param typeDemande Le type de demande
     * @return Le nombre de demandes avec ce statut et ce type
     */
    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = :statut AND d.typeDemande = :typeDemande")
    long countByStatutAndTypeDemande(@Param("statut") String statut, @Param("typeDemande") String typeDemande);



    /**
     * Requêtes spécifiques pour chaque type de demande validée
     * Ces requêtes peuvent être utilisées comme alternatives si les noms des types
     * dans votre base de données sont différents
     */

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 'VALIDÉ' AND (d.typeDemande LIKE '%PERMIS%' OR d.typeDemande LIKE '%PECHE%')")
    long countPermispechesValides();

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 'VALIDÉ' AND (d.typeDemande LIKE '%LICENCE%' AND d.typeDemande LIKE '%GUIDE%')")
    long countLicencesGuidesValidees();

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 'VALIDÉ' AND (d.typeDemande LIKE '%LICENCE%' AND d.typeDemande LIKE '%COMMERCIALE%')")
    long countLicencesCommercialesValidees();

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 'VALIDÉ' AND d.typeDemande LIKE '%CONCESSION%'")
    long countConcessionsValidees();

    @Query("SELECT COUNT(d) FROM Demande d WHERE d.statut = 'VALIDÉ' AND (d.typeDemande LIKE '%CREATION%' AND d.typeDemande LIKE '%ETABLISSEMENT%')")
    long countCreationsEtablissementsValidees();

}



