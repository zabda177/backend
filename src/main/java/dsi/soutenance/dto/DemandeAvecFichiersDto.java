package dsi.soutenance.dto;

import java.util.ArrayList;
import java.util.List;

import dsi.soutenance.model.DemandeurMorale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Ajoute un constructeur par défaut
@Builder
public class DemandeAvecFichiersDto {
    private String ifu;
    private String nomResponsable;
    private String denomination;
    private String siege;
    private String telephone1;
    private String telephone2;
    private String mail;
    private List<PieceJointeDto> pieceJointes;

    public static DemandeAvecFichiersDto fromEntity(DemandeurMorale demandeurMorale) {
        return DemandeAvecFichiersDto.builder()
                .ifu(demandeurMorale.getIfu())
                .nomResponsable(demandeurMorale.getNomResponsable())
                .denomination(demandeurMorale.getDenomination())
                .siege(demandeurMorale.getSiege())
                .telephone1(demandeurMorale.getTelephone1())
                .telephone2(demandeurMorale.getTelephone2())
                .mail(demandeurMorale.getMail())
                // Mapper les pièces jointes si nécessaire
                .build();
    }

    public DemandeurMorale toEntity() {
        return DemandeurMorale.builder()
                .ifu(this.ifu)
                .nomResponsable(this.nomResponsable)
                .denomination(this.denomination)
                .siege(this.siege)
                .telephone1(this.telephone1)
                .telephone2(this.telephone2)
                .mail(this.mail)
                .build();
    }
}
