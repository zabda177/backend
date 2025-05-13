package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import projet_soutenance.dsi.DTO.PersonnePhysiqueDTO;
import projet_soutenance.dsi.model.PersonnePhysique;

@Mapper(componentModel= "spring")
public interface PersonnePhysiqueMapper {

    PersonnePhysiqueDTO toDTO(PersonnePhysique personnePhysique);
    PersonnePhysique toEntity(PersonnePhysiqueDTO personnePhysiqueDTO);
}
