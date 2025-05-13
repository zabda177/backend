package projet_soutenance.dsi.mapper;

import org.mapstruct.Mapper;
import projet_soutenance.dsi.DTO.RegionDTO;
import projet_soutenance.dsi.model.Region;

@Mapper(componentModel= "spring")
public interface RegionMapper {
    RegionDTO toDTO(Region region);
    Region toEntity(RegionDTO regionDTO);
}
