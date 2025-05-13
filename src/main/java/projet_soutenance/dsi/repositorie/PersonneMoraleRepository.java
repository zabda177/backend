package projet_soutenance.dsi.repositorie;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.PersonneMorale;

@Repository
public interface PersonneMoraleRepository extends JpaRepository<PersonneMorale, Long>
{
}
