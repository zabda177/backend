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
    private String categorie;
    private String typeDemande;
    private String statut;
    private Long numeroDemande;
    private String codeDemande;
    private Long prix;
    private LocalDate dateDepot;
    private LocalDate dateValidation;
    private List<PieceJointeDto> piecesJointes;

    public  static  DemandeDto fromEntity(Demande demande) {
        return DemandeDto.builder()
                .id(demande.getId())
                .categorie(demande.getCategorie())
                .typeDemande(demande.getTypeDemande())
                .statut(demande.getStatut())
                .prix(demande.getPrix())
                .numeroDemande(demande.getNumeroDemande())
                .codeDemande(demande.getCodeDemande())
                .dateDepot(demande.getDateDepot())
                .dateValidation(demande.getDateValidation())
                .build();
    }

    public  Demande toEntity() {
        return   Demande.builder()
                .id(this.getId())
                .categorie(this.categorie)
                .typeDemande(this.typeDemande)
                .statut(this.statut)
                .prix(this.prix)
                .numeroDemande(this.numeroDemande)
                .codeDemande(this.codeDemande)
                .dateDepot(this.dateDepot)
                .dateValidation(this.dateValidation)
                .build();
    }


}
