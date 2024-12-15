package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.MaterielDto;
import dsi.soutenance.model.Materiel;
import dsi.soutenance.repositorie.MaterielRepository;
import dsi.soutenance.service.MaterielService;

@Service
public class MaterielServiceImpl implements MaterielService {
   
      @Autowired
    private MaterielRepository materielRepository;

    @Override
    public Long save(MaterielDto materielDto) {
        Materiel materiel = MaterielDto.toEntity(materielDto);
        return materielRepository.save(materiel).getId();
    }

    @Override
    public void saveAll(List<MaterielDto> materielDtos) {
        materielDtos.forEach(
                materielDto -> {
                    Materiel materiel = MaterielDto.toEntity(materielDto);
                    materielRepository.save(materiel);
                }
        );
    }

    @Override
    public List<MaterielDto> getAll() {
        return materielRepository.findAll().stream().map(MaterielDto::fromEntity).toList();
    }

    @Override
    public MaterielDto getById(Long id) {
        return materielRepository.findById(id).map(MaterielDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        materielRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<MaterielDto> materielDtos) {
        materielDtos.forEach(materielDto -> {
                    Materiel materiel = MaterielDto.toEntity(materielDto);
                    materielRepository.delete(materiel);
                }
        );
    }
}
