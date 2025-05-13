package projet_soutenance.dsi.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class DemandeDTO {
    private Long id;
    private String typeDemande;
    private String typeDemandeur;
    private String statut;
    private Long prix;
    private Long numeroDemande;
    private String codeDemande;
    private LocalDate dateDepot;
    private LocalDate dateValidation;
    private String motifRejet;
    private  LocalDate dateRejet;

    private List<PieceJointeDTO> pieceJointeDTO;
    private PersonneMoraleDTO personneMoraleDTO;
    private PersonnePhysiqueDTO personnePhysiqueDTO;
    private CommuneDDTO communeDTO;


    private Integer step;
    private String statusLabel;

    // Getters et setters existants...

    // Getters et setters pour les nouveaux champs
    public Integer getStep() {
        return step;
    }

}
