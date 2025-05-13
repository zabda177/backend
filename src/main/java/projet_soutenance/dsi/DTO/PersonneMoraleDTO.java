package projet_soutenance.dsi.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonneMoraleDTO {

    private Long id;
    private String nomResponsable;
    private String prenomResponsable;
    private String denomination;
    private String siege;
    private String ifu;
    private String mailPersonneMorale;
    private String telephone1PersonneMorale;
    private String telephone2PersonneMorale;
}
