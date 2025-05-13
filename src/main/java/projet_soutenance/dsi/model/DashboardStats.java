package projet_soutenance.dsi.model;


import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;


@Data
public class DashboardStats {
    private long demandeEncours;
    private long demandeAccepte;
    private long demandeValide;
    private long demandeRejete;
    private long total;


    /**
     * Calcule automatiquement le total des demandes
     */
    public long getTotal() {
        return demandeEncours + demandeAccepte + demandeValide + demandeRejete;
    }
}
