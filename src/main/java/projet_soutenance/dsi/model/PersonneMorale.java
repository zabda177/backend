package projet_soutenance.dsi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PersonneMorale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomResponsable;
    private String prenomResponsable;
    private String denomination;
    private String siege;
    private String ifu;
    private String mailPersonneMorale;
    private String telephone1PersonneMorale;
    private String telephone2PersonneMorale;
    private boolean deleted = false;

    @OneToMany(mappedBy="personneMorale")
        private List <Demande> demandes;
}

