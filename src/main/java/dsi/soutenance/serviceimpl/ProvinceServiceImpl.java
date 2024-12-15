package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.ProvinceDto;
import dsi.soutenance.model.Province;
import dsi.soutenance.repositorie.ProvinceRepository;
import dsi.soutenance.service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService {
   
     @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Long save(ProvinceDto provinceDto) {
        Province province = ProvinceDto.toEntity(provinceDto);
        return provinceRepository.save(province).getId();

    }

    @Override
    public void saveAll(List<ProvinceDto> provinceDtos) {
        provinceDtos.forEach(
                provinceDto -> {
                    Province province = ProvinceDto.toEntity(provinceDto);
                    provinceRepository.save(province);
                }
        );
    }

    @Override
    public List<ProvinceDto> getAll() {
        return provinceRepository.findAll().stream().map(ProvinceDto::fromEntity).toList();

    }

    @Override
    public ProvinceDto getById(Long id) {
        return provinceRepository.findById(id).map(ProvinceDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        provinceRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<ProvinceDto> provinceDtos) {
        provinceDtos.forEach(provinceDto -> {
            Province province = ProvinceDto.toEntity(provinceDto);
            provinceRepository.delete(province);
        });
    }
}
