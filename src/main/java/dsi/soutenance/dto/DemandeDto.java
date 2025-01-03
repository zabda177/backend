package dsi.soutenance.dto;

import dsi.soutenance.model.Demande;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DemandeDto {
    private long id;
    private String typeDemande;
    private String typeDemandeur;
    private String statut;
    private Long numeroDemande;
    private String codeDemande;
    private Long prix;
    private LocalDate dateDepot;
    private LocalDate dateValidation;

    //private List<PieceJointeDto> piecesJointes;

    public  static  DemandeDto fromEntity(Demande demande) {
        return DemandeDto.builder()
                .id(demande.getId())
                .typeDemande(demande.getTypeDemande())
                .typeDemandeur(demande.getTypeDemandeur())
                .statut(demande.getStatut())
                .prix(demande.getPrix())
                .numeroDemande(demande.getNumeroDemande())
                .codeDemande(demande.getCodeDemande())
                .dateDepot(demande.getDateDepot())
                .dateValidation(demande.getDateValidation())

                .build();
    }

    public  Demande toEntity(DemandeDto demandeDto) {
        return   Demande.builder()
                .id(demandeDto.getId())
                .typeDemande(demandeDto.getTypeDemande())
                .typeDemandeur(demandeDto.getTypeDemandeur())
                .statut(demandeDto.getStatut())
                .prix(demandeDto.getPrix())
                .numeroDemande(demandeDto.getNumeroDemande())
                .codeDemande(demandeDto.getCodeDemande())
                .dateDepot(demandeDto.getDateDepot())
                .dateValidation(demandeDto.getDateValidation())
                .build();
    }


}
