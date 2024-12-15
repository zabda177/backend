package dsi.soutenance.repositorie;

import dsi.soutenance.model.DemandeurMorale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeurMoraleRepository extends JpaRepository <DemandeurMorale, Long> {
    Long deleteByDemandeId(Long id);
}
