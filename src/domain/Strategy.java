package domain;

public class Strategy{

    private String idStrategy;
    private String action;
    private String description;
    private String goal;
    private int number;
    private String result;
    private String idTarget;

    public Strategy(String idStrategy, String action, String description, String goal, int number, String result, String idTarget) {
        this.idStrategy = idStrategy;
        this.action = action;
        this.description = description;
        this.goal = goal;
        this.number = number;
        this.result = result;
        this.idTarget = idTarget;
    }

    public Strategy(String action, String description, String goal, int number, String result, String idTarget) {
        this.action = action;
        this.description = description;
        this.goal = goal;
        this.number = number;
        this.result = result;
        this.idTarget = idTarget;
    }

    public Strategy(String action, String description, String goal, String result) {
        this.action = action;
        this.description = description;
        this.goal = goal;
        this.result = result;
    }

    public Strategy(String action, String description, String goal, int number, String result) {
        this.action = action;
        this.description = description;
        this.goal = goal;
        this.number = number;
        this.result = result;
    }

    public String getIdStrategy() {
        return idStrategy;
    }

    public void setIdStrategy(String idStrategy) {
        this.idStrategy = idStrategy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }


    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Strategy.class){
            return false;
        }

        Strategy strategy = (Strategy) object;

        if(strategy.getIdStrategy().equals(this.idStrategy) &&
                strategy.getAction().equals(this.action) &&
                strategy.getDescription().equals(this.description) &&
                strategy.getGoal().equals(this.goal) &&
                strategy.getNumber() == this.number &&
                strategy.getResult().equals(this.result) &&
                strategy.getIdTarget().equals(this.idTarget)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "idStrategy='" + idStrategy + '\'' +
                ", action='" + action + '\'' +
                ", description='" + description + '\'' +
                ", goal='" + goal + '\'' +
                ", number=" + number +
                ", result='" + result + '\'' +
                ", idTarget='" + idTarget + '\'' +
                '}';
    }
}
