package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.DemandeurPhysiqueDto;
import dsi.soutenance.model.DemandeurPhysique;
import dsi.soutenance.repositorie.DemandeurPhysiqueRepository;
import dsi.soutenance.service.DemandeurPhysiqueService;


@Service
public class DemandeurPhysiqueServiceImpl implements DemandeurPhysiqueService {
  @Autowired
  private DemandeurPhysiqueRepository demandeurPhysiqueRepository;

    @Override
    public Long save(DemandeurPhysiqueDto demandeurPhysiqueDto) {
        DemandeurPhysique  demandeurPhysique= DemandeurPhysiqueDto.toEntity(demandeurPhysiqueDto);
        return demandeurPhysiqueRepository.save(demandeurPhysique).getId();
    }

    @Override
    public void saveAll(List<DemandeurPhysiqueDto> demandeurPhysiqueDtos) {
        demandeurPhysiqueDtos.forEach(
                demandeurPhysiqueDto -> {
                    DemandeurPhysique demandeurPhysique = DemandeurPhysiqueDto.toEntity(demandeurPhysiqueDto);
                    demandeurPhysiqueRepository.save(demandeurPhysique);
                }
        );
    }

    @Override
    public List<DemandeurPhysiqueDto> getAll() {
        return demandeurPhysiqueRepository.findAll().stream().map(DemandeurPhysiqueDto::fromEntity).toList();
    }

    @Override
    public DemandeurPhysiqueDto getById(Long id) {
        return demandeurPhysiqueRepository.findById(id).map(DemandeurPhysiqueDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        demandeurPhysiqueRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<DemandeurPhysiqueDto> demandeurPhysiqueDtos) {
        demandeurPhysiqueDtos.forEach(demandeurPhysiqueDto -> {
                    DemandeurPhysique demandeurPhysique = DemandeurPhysiqueDto.toEntity(demandeurPhysiqueDto);
                    demandeurPhysiqueRepository.delete(demandeurPhysique);
                }
        );
    }





}
