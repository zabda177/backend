package dsi.soutenance.dto;

import dsi.soutenance.model.Commune;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommuneDto {
    private long id;
    private String libelle;
    private Long codeDgess;

    public  static CommuneDto fromEntity(Commune commune) {
        return CommuneDto.builder()
                .id(commune.getId())
                .libelle(commune.getLibelle())
                .codeDgess(commune.getCodeDgess())
                .build();
    }

    public  static Commune toEntity(CommuneDto communeDto) {
        return  Commune.builder()
                .id(communeDto.getId())
                .libelle(communeDto.libelle)
                .codeDgess(communeDto.codeDgess)
                .build();
    }
}
