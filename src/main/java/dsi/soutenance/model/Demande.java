package dsi.soutenance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLDelete(sql = "UPDATE ....")
@Where(clause = "deleted=false")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categorie;
    private String typeDemande;
    private String statut;
    private Long prix;
    private Long numeroDemande;
    private String codeDemande;
    private LocalDate dateDepot;
    private LocalDate dateValidation;
    private boolean deleted = false;

    @OneToMany(mappedBy = "demande")
    private List<Materiel> materiels;

    @OneToMany(mappedBy = "demande")
    private List<PieceJointe> pieceJointe;

    @OneToOne(mappedBy = "demande")
    private DemandeurMorale demandeurMorale;

    @OneToOne(mappedBy = "demande")
    private DemandeurPhysique demandeurPhysique;

    @ManyToOne
    private Commune commune;
}
