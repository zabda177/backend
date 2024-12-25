package dsi.soutenance.service;

import java.util.List;
import java.util.Map;

import dsi.soutenance.dto.DashboardStats;
import dsi.soutenance.dto.DemandeDto;
import dsi.soutenance.dto.SoumissionDto;

public interface DemandeService extends AbstractService<DemandeDto> {
    public DemandeDto saveSoumission(SoumissionDto soumissionDto);

    public List<SoumissionDto> findAll();

    public List<DemandeDto> getDemandesByRegion(Long regionId);

    List<SoumissionDto> findByIdAndStatutEnCoursDeTraitement();

    List<SoumissionDto> findById(Long id);

    Long deleteByDemandeurPhysiqueId(Long id);

    Long deleteByDemandeurMoraleId(Long id);

    List<SoumissionDto> findDemandeByNumeroAndCode(Long numeroDemande, String codeDemande);

    public SoumissionDto accepteDemande(Long id);

    public SoumissionDto rejeteDemande(Long id);

    public SoumissionDto valideDemande(Long id);

    public List<SoumissionDto> getDemandeAccepter();

    public List<SoumissionDto> getDemandeRejetee();

    public List<SoumissionDto> getDemandeValide();

    public List<SoumissionDto> getDemandeAcceptee();

    public DashboardStats getDashboardStats();

    public   Map<String, Object> getDemandeDetails(Long demandeId);

}