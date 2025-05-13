package projet_soutenance.dsi.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projet_soutenance.dsi.DTO.DemandeDTO;
import projet_soutenance.dsi.model.Demande;

@Mapper(componentModel= "spring", uses = {PieceJointeMapper.class, PersonneMoraleMapper.class, PersonnePhysiqueMapper.class, CommuneMapper.class})
public interface DemandeMapper {
    @Mapping(source = "pieceJointe", target = "pieceJointeDTO")
    @Mapping(source = "personneMorale", target = "personneMoraleDTO")
    @Mapping(source = "personnePhysique", target = "personnePhysiqueDTO")
    @Mapping(source = "commune", target = "communeDTO")
    DemandeDTO toDTO(Demande demande);

    @Mapping(source = "pieceJointeDTO", target = "pieceJointe")
    @Mapping(source = "personneMoraleDTO", target = "personneMorale")
    @Mapping(source = "personnePhysiqueDTO", target = "personnePhysique")
    @Mapping(source = "communeDTO", target = "commune")
    Demande toEntity(DemandeDTO demandeDTO);
    
}
