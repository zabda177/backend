package projet_soutenance.dsi.repositorie;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.PersonnePhysique;

@Repository
public interface PersonnePhysiqueRepository extends JpaRepository<PersonnePhysique,Long> {

}

