//package projet_soutenance.dsi.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import projet_soutenance.dsi.model.DashboardStats;
//import projet_soutenance.dsi.model.StatusDemande;
//import projet_soutenance.dsi.repositorie.DashboardRepository;
//import projet_soutenance.dsi.repositorie.DemandeRepository;
//
//
//@Service
//public class DashboardService {
//    @Autowired
//    private DashboardRepository dashboardRepository;
//
//    /**
//     * Calcule les statistiques pour le tableau de bord
//     * @return Un objet contenant les nombres de demandes dans chaque état
//     */
//    public DashboardStats getDashboardStats() {
//        DashboardStats stats = new DashboardStats();
//
//        try {
//            // Utilisez directement les chaînes de caractères correspondant aux valeurs dans votre base de données
//            // En fonction du champ "statut" dans votre entité Demande
//            long demandesEnCours = dashboardRepository.countByStatut("EN_COURS");
//            stats.setDemandeEncours(demandesEnCours);
//
//            long demandesAcceptees = dashboardRepository.countByStatut("ACCEPTÉ");
//            stats.setDemandeAccepte(demandesAcceptees);
//
//            long demandesValidees = dashboardRepository.countByStatut("VALIDÉ");
//            stats.setDemandeValide(demandesValidees);
//
//            long demandesRejetees = dashboardRepository.countByStatut("REJETÉ");
//            stats.setDemandeRejete(demandesRejetees);
//        } catch (Exception e) {
//            // Log l'erreur
//            System.err.println("Erreur lors du calcul des statistiques: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return stats;
//    }
//
//    /**
//     * Calcule les statistiques des situations de demandes validées par type
//     * @return Un objet SituationStats contenant les totaux par type de demande validée
//     */
//    public SituationStats getSituationStats() {
//        SituationStats situationStats = new SituationStats();
//
//        try {
//            // Compter les demandes validées par type de demande
//            // Assurez-vous que les valeurs correspondent exactement à celles dans votre base de données
//
//            long totalPermispeche = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "PERMIS_PECHE");
//            situationStats.setTotalPermispeche(totalPermispeche);
//
//            long totalLicenceGuide = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "LICENCE_GUIDE");
//            situationStats.setTotalLicenceGuide(totalLicenceGuide);
//
//            long totalLicenceCommerciale = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "LICENCE_COMMERCIALE");
//            situationStats.setTotalLicenceCommerciale(totalLicenceCommerciale);
//
//            long totalConcession = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "CONCESSION");
//            situationStats.setTotalConcession(totalConcession);
//
//            long totalCreationEtablissement = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "CREATION_ETABLISSEMENT");
//            situationStats.setTotalCreationEtablissement(totalCreationEtablissement);
//
//            System.out.println("Statistiques des situations calculées: " + situationStats.toString());
//
//        } catch (Exception e) {
//            // Log l'erreur
//            System.err.println("Erreur lors du calcul des statistiques de situation: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return situationStats;
//    }
//
//
//
//}
//
package projet_soutenance.dsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.model.DashboardStats;
import projet_soutenance.dsi.model.SituationStats;
import projet_soutenance.dsi.repositorie.DashboardRepository;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    /**
     * Calcule les statistiques pour le tableau de bord
     * @return Un objet contenant les nombres de demandes dans chaque état
     */
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        try {
            // Utilisez directement les chaînes de caractères correspondant aux valeurs dans votre base de données
            // En fonction du champ "statut" dans votre entité Demande
            long demandesEnCours = dashboardRepository.countByStatut("EN_COURS");
            stats.setDemandeEncours(demandesEnCours);

            long demandesAcceptees = dashboardRepository.countByStatut("ACCEPTÉ");
            stats.setDemandeAccepte(demandesAcceptees);

            long demandesValidees = dashboardRepository.countByStatut("VALIDÉ");
            stats.setDemandeValide(demandesValidees);

            long demandesRejetees = dashboardRepository.countByStatut("REJETÉ");
            stats.setDemandeRejete(demandesRejetees);
        } catch (Exception e) {
            // Log l'erreur
            System.err.println("Erreur lors du calcul des statistiques: " + e.getMessage());
            e.printStackTrace();
        }

        return stats;
    }

    /**
     * Calcule les statistiques des situations de demandes validées par type
     * @return Un objet SituationStats contenant les totaux par type de demande validée
     */
    public SituationStats getSituationStats() {
        SituationStats situationStats = new SituationStats();

        try {
            // Compter les demandes validées par type de demande
            // Assurez-vous que les valeurs correspondent exactement à celles dans votre base de données

            long totalPermispeche = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "PERMIS_PECHE");
            situationStats.setTotalPermispeche(totalPermispeche);

            long totalLicenceGuide = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "LICENCE_GUIDE");
            situationStats.setTotalLicenceGuide(totalLicenceGuide);

            long totalLicenceCommerciale = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "LICENCE_COMMERCIALE");
            situationStats.setTotalLicenceCommerciale(totalLicenceCommerciale);

            long totalConcession = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "CONCESSION");
            situationStats.setTotalConcession(totalConcession);

            long totalCreationEtablissement = dashboardRepository.countByStatutAndTypeDemande("VALIDÉ", "CREATION_ETABLISSEMENT");
            situationStats.setTotalCreationEtablissement(totalCreationEtablissement);

            System.out.println("Statistiques des situations calculées: " + situationStats.toString());

        } catch (Exception e) {
            // Log l'erreur
            System.err.println("Erreur lors du calcul des statistiques de situation: " + e.getMessage());
            e.printStackTrace();
        }

        return situationStats;
    }
}