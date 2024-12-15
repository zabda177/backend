package dsi.soutenance.dto;

import dsi.soutenance.model.DemandeurPhysique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DemandeurPhysiqueDto {
    private Long id;
    private String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaisse;
    private String lieuResidence;
    private String telephone1;
    private String telephone2;
    private String mail;
    private String typePiece;
    private String numPiece;

    public static DemandeurPhysiqueDto fromEntity(DemandeurPhysique demandeurPhysique) {
        return DemandeurPhysiqueDto.builder()
                .id(demandeurPhysique.getId())
                .nom(demandeurPhysique.getNom())
                .prenom(demandeurPhysique.getPrenom())
                .genre(demandeurPhysique.getGenre())
                .dateNaisse(demandeurPhysique.getDateNaisse())
                .lieuResidence(demandeurPhysique.getLieuResidence())
                .telephone1(demandeurPhysique.getTelephoneP1())
                .telephone2(demandeurPhysique.getTelephoneP2())
                .mail(demandeurPhysique.getMailP())
                .typePiece(demandeurPhysique.getTypePiece())
                .numPiece(demandeurPhysique.getNumPiece())
                .build();
    }

    public static DemandeurPhysique toEntity(DemandeurPhysiqueDto demandeurPhysiqueDto) {
        return DemandeurPhysique.builder()
                .id(demandeurPhysiqueDto.getId())
                .nom(demandeurPhysiqueDto.nom)
                .prenom(demandeurPhysiqueDto.prenom)
                .genre(demandeurPhysiqueDto.genre)
                .dateNaisse(demandeurPhysiqueDto.dateNaisse)
                .lieuResidence(demandeurPhysiqueDto.lieuResidence)
                .telephoneP1(demandeurPhysiqueDto.telephone1)
                .telephoneP2(demandeurPhysiqueDto.telephone2)
                .mailP(demandeurPhysiqueDto.mail)
                .typePiece(demandeurPhysiqueDto.typePiece)
                .numPiece(demandeurPhysiqueDto.numPiece)
                .build();
    }
}
