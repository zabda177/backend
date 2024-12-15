package dsi.soutenance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStats {
    private long totalDemandes;
    private long demandesRejetees;
    private long demandesValidees;
    private long demandesAcceptees;
    private long demandesEncours;

}
