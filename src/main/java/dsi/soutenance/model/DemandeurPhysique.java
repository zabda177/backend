package dsi.soutenance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@SQLDelete(sql = "UPDATE ....")
@Where(clause = "deleted=false")
public class DemandeurPhysique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaisse;
    private String lieuResidence;
    private String telephoneP1;
    private String telephoneP2;
    private String mailP;
    private String typePiece;
    private String numPiece;
    private boolean deleted = false;

    @OneToOne
    @JoinColumn(name = "personne_physique_Id")
    private Demande demande;

}
