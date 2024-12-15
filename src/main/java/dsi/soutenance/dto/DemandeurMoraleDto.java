package dsi.soutenance.dto;

import dsi.soutenance.model.DemandeurMorale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DemandeurMoraleDto {

    private Long id;
    private String ifu;
    private String nomResponsable;
    private String denomination;
    private String siege;
    private String mail;
    private String telephone1;
    private String telephone2;

    public static DemandeurMoraleDto fromEntity(DemandeurMorale demandeurMorale) {
        return DemandeurMoraleDto.builder()
                .id(demandeurMorale.getId())
                .ifu(demandeurMorale.getIfu())
                .denomination(demandeurMorale.getDenomination())
                .siege(demandeurMorale.getSiege())
                .nomResponsable(demandeurMorale.getNomResponsable())
                .mail(demandeurMorale.getMail())
                .telephone1(demandeurMorale.getTelephone1())
                .telephone2(demandeurMorale.getTelephone2())
                .build();

    }

    public static DemandeurMorale toEntity(DemandeurMoraleDto demandeurMoraleDto) {
        return DemandeurMorale.builder()
                .id(demandeurMoraleDto.getId())
                .ifu(demandeurMoraleDto.ifu)
                .nomResponsable(demandeurMoraleDto.nomResponsable)
                .denomination(demandeurMoraleDto.denomination)
                .siege(demandeurMoraleDto.siege)
                .mail(demandeurMoraleDto.mail)
                .telephone1(demandeurMoraleDto.telephone1)
                .telephone2(demandeurMoraleDto.telephone2)
                .build();
    }
}
