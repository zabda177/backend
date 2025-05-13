package projet_soutenance.dsi.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class PieceJointeDTO {
    private Long id;
    private String libelle;
    private String url;

    private Long demandeId;

}
