package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projet_soutenance.dsi.DTO.PieceJointeDTO;
import projet_soutenance.dsi.model.PieceJointe;

@Mapper(componentModel= "spring", uses = {DemandeMapper.class})
public interface PieceJointeMapper {

    @Mapping(source = "demande.id", target = "demandeId")
    PieceJointeDTO toDTO(PieceJointe pieceJointe);

    @Mapping(target = "demande", ignore = true)
    @Mapping(target = "fichier", ignore = true)
    PieceJointe toEntity(PieceJointeDTO pieceJointeDTO);
}

