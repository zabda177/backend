package projet_soutenance.dsi.model;

public class SituationStats {

    private long totalPermispeche;
    private long totalLicenceGuide;
    private long totalLicenceCommerciale;
    private long totalConcession;
    private long totalCreationEtablissement;

    // Constructeur par défaut
    public SituationStats() {
    }

    // Constructeur avec tous les paramètres
    public SituationStats(long totalPermispeche, long totalLicenceGuide,
                          long totalLicenceCommerciale, long totalConcession,
                          long totalCreationEtablissement) {
        this.totalPermispeche = totalPermispeche;
        this.totalLicenceGuide = totalLicenceGuide;
        this.totalLicenceCommerciale = totalLicenceCommerciale;
        this.totalConcession = totalConcession;
        this.totalCreationEtablissement = totalCreationEtablissement;
    }

    // Getters et Setters
    public long getTotalPermispeche() {
        return totalPermispeche;
    }

    public void setTotalPermispeche(long totalPermispeche) {
        this.totalPermispeche = totalPermispeche;
    }

    public long getTotalLicenceGuide() {
        return totalLicenceGuide;
    }

    public void setTotalLicenceGuide(long totalLicenceGuide) {
        this.totalLicenceGuide = totalLicenceGuide;
    }

    public long getTotalLicenceCommerciale() {
        return totalLicenceCommerciale;
    }

    public void setTotalLicenceCommerciale(long totalLicenceCommerciale) {
        this.totalLicenceCommerciale = totalLicenceCommerciale;
    }

    public long getTotalConcession() {
        return totalConcession;
    }

    public void setTotalConcession(long totalConcession) {
        this.totalConcession = totalConcession;
    }

    public long getTotalCreationEtablissement() {
        return totalCreationEtablissement;
    }

    public void setTotalCreationEtablissement(long totalCreationEtablissement) {
        this.totalCreationEtablissement = totalCreationEtablissement;
    }

    /**
     * Calcule le total général de toutes les demandes validées
     * @return Le total de toutes les demandes validées
     */
    public long getTotalGeneral() {
        return totalPermispeche + totalLicenceGuide + totalLicenceCommerciale +
                totalConcession + totalCreationEtablissement;
    }

    @Override
    public String toString() {
        return "SituationStats{" +
                "totalPermispeche=" + totalPermispeche +
                ", totalLicenceGuide=" + totalLicenceGuide +
                ", totalLicenceCommerciale=" + totalLicenceCommerciale +
                ", totalConcession=" + totalConcession +
                ", totalCreationEtablissement=" + totalCreationEtablissement +
                ", totalGeneral=" + getTotalGeneral() +
                '}';
    }
}
