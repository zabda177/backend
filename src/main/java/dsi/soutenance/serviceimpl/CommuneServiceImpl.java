package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.CommuneDto;
import dsi.soutenance.model.Commune;
import dsi.soutenance.repositorie.CommuneRepository;
import dsi.soutenance.service.CommuneService;

@Service
public class CommuneServiceImpl implements CommuneService {
    @Autowired
private CommuneRepository communeRepository;


    @Override
    public Long save(CommuneDto communeDto){
        Commune commune = CommuneDto.toEntity(communeDto);
        return  communeRepository.save(commune).getId();
    }

    @Override
    public void saveAll(List<CommuneDto> communeDtos) {
        communeDtos.forEach(
            CommuneDto -> {
                Commune commune = CommuneDto.toEntity(CommuneDto);
                communeRepository.save(commune);
            }
        );
    }

    @Override
    public List<CommuneDto> getAll() {
        return communeRepository.findAll().stream().map(CommuneDto::fromEntity).toList();
    }

   

    @Override
    public CommuneDto getById(Long id) {
        return communeRepository.findById(id).map(CommuneDto::fromEntity).orElse(null);
    }


    @Override
    public void deleteById(Long id) {
        communeRepository.deleteById(id);
    }


    @Override
    public void deleteAll(List<CommuneDto> y) {

    }
}
