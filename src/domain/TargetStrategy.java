package domain;

public class TargetStrategy{

    private String idTargetStrategy;
    private String idTarget;
    private String idStrategy;

    public TargetStrategy(String idTargetStrategy, String idTarget, String idStrategy) {
        this.idTargetStrategy = idTargetStrategy;
        this.idTarget = idTarget;
        this.idStrategy = idStrategy;
    }

    public String getIdTargetStrategy() {
        return idTargetStrategy;
    }

    public void setIdTargetStrategy(String idTargetStrategy) {
        this.idTargetStrategy = idTargetStrategy;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(String idStrategy) {
        this.idStrategy = idStrategy;
    }

    @Override
    public String toString() {
        return "TargetStrategy{" +
                "idTargetStrategy='" + idTargetStrategy + '\'' +
                ", idTarget='" + idTarget + '\'' +
                ", idStrategy='" + idStrategy + '\'' +
                '}';
    }
}
