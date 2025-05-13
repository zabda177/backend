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
}



