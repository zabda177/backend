package projet_soutenance.dsi.service;


import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet_soutenance.dsi.DTO.RegionDTO;
import projet_soutenance.dsi.mapper.RegionMapper;
import projet_soutenance.dsi.model.Region;
import projet_soutenance.dsi.repositorie.RegionRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Autowired
    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    public List<RegionDTO> getAllRegions() {
        return regionRepository.findAll().stream()
                .filter(region -> !region.getDelete())
                .map(regionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<RegionDTO> getRegionById(Long id) {
        return regionRepository.findById(id)
                .filter(region -> !region.getDelete())
                .map(regionMapper::toDTO);
    }

    @Transactional
    public RegionDTO createRegion(RegionDTO regionDTO) {
        Region region = regionMapper.toEntity(regionDTO);
        region.setDelete(false);
        Region savedRegion = regionRepository.save(region);
        return regionMapper.toDTO(savedRegion);
    }

    @Transactional
    public Optional<RegionDTO> updateRegion(Long id, RegionDTO regionDTO) {
        return regionRepository.findById(id)
                .filter(region -> !region.getDelete())
                .map(region -> {
                    region.setLibelle(regionDTO.getLibelle());
                    region.setCodeDgess(regionDTO.getCodeDgess());
                    return regionMapper.toDTO(regionRepository.save(region));
                });
    }

    @Transactional
    public boolean deleteRegion(Long id) {
        return regionRepository.findById(id)
                .map(region -> {
                    region.setDelete(true);
                    regionRepository.save(region);
                    return true;
                })
                .orElse(false);
    }
}
