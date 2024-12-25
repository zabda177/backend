package dsi.soutenance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private MultipartFile file;
    private Long demandeId;

//
//    public static PieceJointeDto fromEntity(PieceJointe pieceJointe) {
//        return PieceJointeDto.builder()
//                //.id(pieceJointe.getId())
//                .libelle(pieceJointe.getLibelle())
//                .url(pieceJointe.getUrl())
//                .build();
//    }
//    public  static PieceJointe toEntity(PieceJointeDto pieceJointeDto) {
//        return PieceJointe.builder()
//                //.id(pieceJointeDto.getId())
//                .libelle(pieceJointeDto.getLibelle())
//                .url(pieceJointeDto.getUrl())
//                .build();
//    }

    public static PieceJointeDto fromEntity(PieceJointe pieceJointe) {
        if (pieceJointe == null) {
            return null;
        }
        return PieceJointeDto.builder()
                .id(pieceJointe.getId())  // Incluez l'ID
                .libelle(pieceJointe.getLibelle())
                .url(pieceJointe.getUrl())
                .demandeId(pieceJointe.getDemande() != null ? pieceJointe.getDemande().getId() : null)
                .build();
    }

    public static PieceJointe toEntity(PieceJointeDto pieceJointeDto) {
        if (pieceJointeDto == null) {
            return null;
        }
        return PieceJointe.builder()
                .id(pieceJointeDto.getId())  // Incluez l'ID
                .libelle(pieceJointeDto.getLibelle())
                .url(pieceJointeDto.getUrl())
                .deleted(false)
                .build();
    }
}
