package projet_soutenance.dsi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE ....")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeDemande;
    private String typeDemandeur;
    private String categorie;
    private String statut;
    private Long prix;
    private Long numeroDemande;
    private String codeDemande;
    private LocalDate dateDepot;
    private String motifRejet;
    private LocalDate dateValidation;
    private LocalDate dateRejet;
    private boolean deleted = false;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personnePhysiqueId")
    private PersonnePhysique personnePhysique;

    @OneToMany(mappedBy = "demande")
    private List<PieceJointe> pieceJointe;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personneMoraleId")
    private PersonneMorale personneMorale;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commune_id")
    private Commune commune;

    @OneToMany(mappedBy = "demande", cascade = CascadeType.ALL)
    private List<PieceJointe> piecesJointes;

}
