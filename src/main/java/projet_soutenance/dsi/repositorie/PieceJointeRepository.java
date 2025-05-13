package projet_soutenance.dsi.repositorie;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.PieceJointe;

import java.util.List;

@Repository
public interface PieceJointeRepository extends JpaRepository <PieceJointe,Long >
{
    List<PieceJointe> findByDemandeIdAndDeletedFalse(Long demandeId);
}
