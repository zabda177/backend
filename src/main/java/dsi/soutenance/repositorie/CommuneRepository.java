package dsi.soutenance.repositorie;

import dsi.soutenance.model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
}
