package dsi.soutenance.repositorie;

import dsi.soutenance.model.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
    Long deleteByDemandeId(Long id);
    List<PieceJointe> findByDemandeId(Long demandeId);
}
