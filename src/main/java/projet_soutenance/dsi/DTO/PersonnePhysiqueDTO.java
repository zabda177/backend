package projet_soutenance.dsi.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PersonnePhysiqueDTO {
    private Long id;
    private  String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaissance;
    private String nationalite;
    private String villeResidance;
    private String telephone1PersonnePhysique;
    private String telephone2PersonnePhysique;
    private String mailPersonnePhysique;
    private String typePiece;
    private String numPiece;

}
