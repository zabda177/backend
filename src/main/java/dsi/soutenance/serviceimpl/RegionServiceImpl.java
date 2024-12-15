package dsi.soutenance.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsi.soutenance.dto.RegionDto;
import dsi.soutenance.model.Region;
import dsi.soutenance.repositorie.RegionRepository;
import dsi.soutenance.service.RegionService;

@Service
public class RegionServiceImpl implements RegionService {
  
    @Autowired
    private RegionRepository regionRepository;

    @Override
    public Long save(RegionDto regionDto) {
        Region region = RegionDto.toEntity(regionDto);
        return regionRepository.save(region).getId();
    }

    @Override
    public void saveAll(List<RegionDto> regionDtos) {
        System.out.println("***");
        regionDtos.forEach(
                regionDto -> {
                    Region region = RegionDto.toEntity(regionDto);
                    regionRepository.save(region);
                }
        );
    }

    @Override
    public List<RegionDto> getAll() {
        return regionRepository.findAll().stream().map(RegionDto::fromEntity).toList();
    }

    @Override
    public RegionDto getById(Long id) {
        return regionRepository.findById(id).map(RegionDto::fromEntity).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        regionRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<RegionDto> regionDtos) {
        regionDtos.forEach(regionDto -> {
            Region region = RegionDto.toEntity(regionDto);
            regionRepository.delete(region);
        });
    }
}
