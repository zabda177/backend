package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.DemandeurMoraleDto;
import dsi.soutenance.model.DemandeurMorale;
import dsi.soutenance.repositorie.DemandeurMoraleRepository;
import dsi.soutenance.service.DemandeurMoraleService;

@Service
public class DemandeurMoraleServiceImpl implements DemandeurMoraleService {
   @Autowired
   private DemandeurMoraleRepository demandeurMoraleRepository;

@Override
    public Long save(DemandeurMoraleDto demandeurMoraleDto) {
        DemandeurMorale  demandeurMorale= DemandeurMoraleDto.toEntity(demandeurMoraleDto);
        return demandeurMoraleRepository.save(demandeurMorale).getId();
    }

    @Override
    public void saveAll(List<DemandeurMoraleDto> demandeurMoraleDtos) {
        demandeurMoraleDtos.forEach(
                demandeurMoraleDto -> {
                    DemandeurMorale demandeurMorale = DemandeurMoraleDto.toEntity(demandeurMoraleDto);
                    demandeurMoraleRepository.save(demandeurMorale);
                }
        );
    }

    @Override
    public List<DemandeurMoraleDto> getAll() {
        return demandeurMoraleRepository.findAll().stream().map(DemandeurMoraleDto::fromEntity).toList();
    }

    @Override
    public DemandeurMoraleDto getById(Long id) {
        return demandeurMoraleRepository.findById(id).map(DemandeurMoraleDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        demandeurMoraleRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<DemandeurMoraleDto> DemandeurMoraleDtos) {
        DemandeurMoraleDtos.forEach(DemandeurMoraleDto -> {
                    DemandeurMorale demandeurMorale = DemandeurMoraleDto.toEntity(DemandeurMoraleDto);
                    demandeurMoraleRepository.delete(demandeurMorale);
                }
        );
    }





}
