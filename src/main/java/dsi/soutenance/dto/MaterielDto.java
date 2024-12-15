package dsi.soutenance.dto;

import dsi.soutenance.model.Materiel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MaterielDto {
    private long id;
    private String libelle;
    private String type;

  public static MaterielDto fromEntity(Materiel materiel){
      return MaterielDto.builder()
              .id(materiel.getId())
              .libelle(materiel.getLibelle())
              .type(materiel.getType())
              .build();
  }
  public  static Materiel toEntity(MaterielDto materielDto) {
      return  Materiel.builder()
              .id(materielDto.getId())
              .libelle(materielDto.libelle)
              .type(materielDto.type)
              .build();
  }
}
