package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import projet_soutenance.dsi.DTO.CommuneDDTO;
import projet_soutenance.dsi.model.Commune;

@Mapper(componentModel= "spring")
public interface CommuneMapper {
    CommuneDDTO toDTO(Commune commune);
    Commune toEntity(CommuneDDTO communeDDTO);
}
