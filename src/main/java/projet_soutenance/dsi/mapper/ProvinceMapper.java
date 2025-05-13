package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import projet_soutenance.dsi.DTO.ProvinceDTO;
import projet_soutenance.dsi.model.Province;

@Mapper(componentModel= "spring")
public interface ProvinceMapper {
    ProvinceDTO toDTO(Province province);
    Province    toEntity(ProvinceDTO provinceDTO);

}
