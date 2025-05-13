package projet_soutenance.dsi.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.experimental.SuperBuilder;



public enum  StatusDemande {
    SOUMISE,
    ACCEPTEE,
    VALIDEE,
    REJETEE
}
