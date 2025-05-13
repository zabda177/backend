package projet_soutenance.dsi.service;


import jakarta.transaction.Transactional;
;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.DTO.ProvinceDTO;
import projet_soutenance.dsi.mapper.ProvinceMapper;
import projet_soutenance.dsi.model.Province;
import projet_soutenance.dsi.repositorie.ProvinceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository, ProvinceMapper provinceMapper) {
        this.provinceRepository = provinceRepository;
        this.provinceMapper = provinceMapper;
    }

    public List<ProvinceDTO> getAllProvinces() {
        return provinceRepository.findAll().stream()
                .filter(province -> !province.getDelete())
                .map(provinceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProvinceDTO> getProvinceById(Long id) {
        return provinceRepository.findById(id)
                .filter(province -> !province.getDelete())
                .map(provinceMapper::toDTO);
    }

    public List<ProvinceDTO> getProvincesByRegionId(Long regionId) {
        return provinceRepository.findByRegionId(regionId).stream()
                .filter(province -> !province.getDelete())
                .map(provinceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProvinceDTO createProvince(ProvinceDTO provinceDTO) {
        Province province = provinceMapper.toEntity(provinceDTO);
        province.setDelete(false);
        Province savedProvince = provinceRepository.save(province);
        return provinceMapper.toDTO(savedProvince);
    }

    @Transactional
    public Optional<ProvinceDTO> updateProvince(Long id, ProvinceDTO provinceDTO) {
        return provinceRepository.findById(id)
                .filter(province -> !province.getDelete())
                .map(province -> {
                    province.setLibelle(provinceDTO.getLibelle());
                    province.setCodeDgess(provinceDTO.getCodeDgess());
                    return provinceMapper.toDTO(provinceRepository.save(province));
                });
    }

    @Transactional
    public boolean deleteProvince(Long id) {
        return provinceRepository.findById(id)
                .map(province -> {
                    province.setDelete(true);
                    provinceRepository.save(province);
                    return true;
                })
                .orElse(false);
    }
}
