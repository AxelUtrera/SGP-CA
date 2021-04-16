package domain;

public class WorkPlanTarget {

    private String idTarget;
    private String keyWorkPlan;

    public WorkPlanTarget(String idTarget, String keyWorkPlan) {
        this.idTarget = idTarget;
        this.keyWorkPlan = keyWorkPlan;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getKeyWorkPlan() {
        return keyWorkPlan;
    }

    public void setKeyWorkPlan(String keyWorkPlan) {
        this.keyWorkPlan = keyWorkPlan;
    }

    @Override
    public String toString() {
        return "WorkplanTarget{" +
                "idTarget='" + idTarget + '\'' +
                ", keyWorkPlan='" + keyWorkPlan + '\'' +
                '}';
    }
}
