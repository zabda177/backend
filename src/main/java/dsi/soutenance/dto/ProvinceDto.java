package dsi.soutenance.dto;

import dsi.soutenance.model.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProvinceDto {
    private long id;
    private String libelle;
    private Long codeDgess;

    public  static ProvinceDto fromEntity(Province province) {
        return  ProvinceDto.builder()
                .id(province.getId())
                .libelle(province.getLibelle())
                .codeDgess(province.getCodeDgess())
                .build();
    }

    public static Province toEntity(ProvinceDto provinceDto){
        return  Province.builder()
                .id(provinceDto.id)
                .libelle(provinceDto.libelle)
                .codeDgess(provinceDto.codeDgess)
                .build();
    }
}
