package dsi.soutenance.dto;

import dsi.soutenance.model.PieceJointe;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PieceJointeDto {
    private long id;
    private String libelle;
    private String url;
    private MultipartFile fichier;



    public static PieceJointeDto fromEntity(PieceJointe pieceJointe) {
        return PieceJointeDto.builder()
                //.id(pieceJointe.getId())
                .libelle(pieceJointe.getLibelle())
                .url(pieceJointe.getUrl())
                .build();
    }
    public  static PieceJointe toEntity(PieceJointeDto pieceJointeDto) {
        return PieceJointe.builder()
                //.id(pieceJointeDto.getId())
                .libelle(pieceJointeDto.libelle)
                .url(pieceJointeDto.url)
                .build();
    }
}
