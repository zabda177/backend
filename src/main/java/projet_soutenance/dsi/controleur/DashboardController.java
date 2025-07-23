//package projet_soutenance.dsi.controleur;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import projet_soutenance.dsi.model.DashboardStats;
//import projet_soutenance.dsi.service.DashboardService;
//import projet_soutenance.dsi.service.DemandeService;
//
//@RestController
//@RequestMapping("/api/dashboard")
//@CrossOrigin(origins = "*")
//public class DashboardController {
//    @Autowired
//    private DashboardService dashboardService;
//
//    @GetMapping("/stats")
//    public ResponseEntity<DashboardStats> getDashboardStats() {
//        try {
//            DashboardStats stats = dashboardService.getDashboardStats();
//            return ResponseEntity.ok(stats);
//        } catch (Exception e) {
//            System.err.println("Erreur dans le contrôleur: " + e.getMessage());
//            e.printStackTrace();
//            // Retourner un objet vide plutôt qu'une erreur 500
//            return ResponseEntity.ok(new DashboardStats());
//        }
//    }
//
//    /**
//     * Récupère les statistiques des situations de demandes validées par type
//     * @return ResponseEntity<SituationStats>
//     */
//    @GetMapping("/situation-stats")
//    public ResponseEntity<SituationStats> getSituationStats() {
//        try {
//            SituationStats situationStats = dashboardService.getSituationStats();
//            return ResponseEntity.ok(situationStats);
//        } catch (Exception e) {
//            System.err.println("Erreur dans le contrôleur getSituationStats: " + e.getMessage());
//            e.printStackTrace();
//            // Retourner un objet vide plutôt qu'une erreur 500
//            return ResponseEntity.ok(new SituationStats());
//        }
//    }
//
//}
package projet_soutenance.dsi.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet_soutenance.dsi.model.DashboardStats;
import projet_soutenance.dsi.model.SituationStats;
import projet_soutenance.dsi.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Récupère les statistiques générales du dashboard
     * @return ResponseEntity<DashboardStats>
     */
    @GetMapping("/stats")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        try {
            DashboardStats stats = dashboardService.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("Erreur dans le contrôleur getDashboardStats: " + e.getMessage());
            e.printStackTrace();
            // Retourner un objet vide plutôt qu'une erreur 500
            return ResponseEntity.ok(new DashboardStats());
        }
    }

    /**
     * Récupère les statistiques des situations de demandes validées par type
     * @return ResponseEntity<SituationStats>
     */
    @GetMapping("/situation-stats")
    public ResponseEntity<SituationStats> getSituationStats() {
        try {
            SituationStats situationStats = dashboardService.getSituationStats();
            return ResponseEntity.ok(situationStats);
        } catch (Exception e) {
            System.err.println("Erreur dans le contrôleur getSituationStats: " + e.getMessage());
            e.printStackTrace();
            // Retourner un objet vide plutôt qu'une erreur 500
            return ResponseEntity.ok(new SituationStats());
        }
    }
}