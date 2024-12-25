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
    private String telephoneP1;
    private String telephoneP2;
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
                .telephoneP1(demandeurPhysique.getTelephoneP1())
                .telephoneP1(demandeurPhysique.getTelephoneP2())
                .mail(demandeurPhysique.getMailP())

                .build();
    }

    public static DemandeurPhysique toEntity(DemandeurPhysiqueDto demandeurPhysiqueDto) {
        return DemandeurPhysique.builder()
                .id(demandeurPhysiqueDto.getId())
                .nom(demandeurPhysiqueDto.getNom())
                .prenom(demandeurPhysiqueDto.getPrenom())
                .genre(demandeurPhysiqueDto.getGenre())
                .dateNaisse(demandeurPhysiqueDto.getDateNaisse())
                .lieuResidence(demandeurPhysiqueDto.getLieuResidence())
                .telephoneP1(demandeurPhysiqueDto.getTelephoneP1())
                .telephoneP2(demandeurPhysiqueDto.getTelephoneP2())
                .mailP(demandeurPhysiqueDto.getMail())

                .build();
    }
}
