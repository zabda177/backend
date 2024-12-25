package dsi.soutenance.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DemandeurMorale")
@SQLDelete(sql = "UPDATE ....")
@Where(clause = "deleted=false")
public class DemandeurMorale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomResponsable;
    private String denomination;
    private String siege;
    private String mail;
    private String telephone1;
    private String telephone2;
    private String ifu;
    private boolean deleted = false;


    @OneToOne(mappedBy="demandeurMorale")
    private Demande demande;
}
