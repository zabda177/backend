package projet_soutenance.dsi.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.Demande;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    @Query("SELECT d FROM Demande d LEFT JOIN FETCH d.pieceJointe")
    List<Demande> findAllWithPieceJointe();


    List<Demande> findByStatutAndDeletedFalse(String statut);

    long countByDeletedFalse();

    long countByStatutAndDeletedFalse(String statut);

    Optional<Demande> findByIdAndCodeDemande(Long id, String codeDemande);
    Optional<Demande> findByCodeDemande(String codeDemande);

}
