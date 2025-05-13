package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import projet_soutenance.dsi.DTO.PersonneMoraleDTO;
import projet_soutenance.dsi.model.PersonneMorale;

@Mapper(componentModel= "spring")
public interface PersonneMoraleMapper {
    PersonneMoraleDTO toDTO(PersonneMorale personneMorale);
    PersonneMorale toEntity(PersonneMoraleDTO personneMoraleDTO);
}
