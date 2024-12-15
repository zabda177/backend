package dsi.soutenance.dto;

import dsi.soutenance.model.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RegionDto {
    private long id;
    private String libelle;
    private Long codeDgess;


    public static RegionDto  fromEntity(Region region) {
        return  RegionDto.builder()
                .id(region.getId())
                .libelle(region.getLibelle())
                .codeDgess(region.getCodeDgess())
                .build();
    }
    public static Region toEntity( RegionDto regionDto) {
        return  Region.builder()
                .id(regionDto.id)
                .libelle(regionDto.libelle)
                .codeDgess(regionDto.codeDgess)
                .build();
    }
}
