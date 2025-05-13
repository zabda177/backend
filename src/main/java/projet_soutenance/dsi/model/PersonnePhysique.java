package projet_soutenance.dsi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PersonnePhysique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
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
    private Boolean deleted = false;

    @OneToMany(mappedBy = "personnePhysique")
    private List<Demande> demandes;

}
