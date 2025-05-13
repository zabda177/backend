package projet_soutenance.dsi.repositorie;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet_soutenance.dsi.model.Province;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByRegionId(Long regionId);
}
