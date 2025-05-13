package projet_soutenance.dsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.model.DashboardStats;
import projet_soutenance.dsi.model.StatusDemande;
import projet_soutenance.dsi.repositorie.DashboardRepository;
import projet_soutenance.dsi.repositorie.DemandeRepository;


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
}

