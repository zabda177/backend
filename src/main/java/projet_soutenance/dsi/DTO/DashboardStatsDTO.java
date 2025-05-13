package projet_soutenance.dsi.DTO;

public class DashboardStatsDTO {
    private long total;
    private long enCours;
    private long acceptees;
    private long rejetees;
    private long validees;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long total, long enCours, long acceptees, long rejetees, long validees) {
        this.total = total;
        this.enCours = enCours;
        this.acceptees = acceptees;
        this.rejetees = rejetees;
        this.validees = validees;
    }

    // Getters and Setters
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getEnCours() {
        return enCours;
    }

    public void setEnCours(long enCours) {
        this.enCours = enCours;
    }

    public long getAcceptees() {
        return acceptees;
    }

    public void setAcceptees(long acceptees) {
        this.acceptees = acceptees;
    }

    public long getRejetees() {
        return rejetees;
    }

    public void setRejetees(long rejetees) {
        this.rejetees = rejetees;
    }

    public long getValidees() {
        return validees;
    }

    public void setValidees(long validees) {
        this.validees = validees;
    }

}
