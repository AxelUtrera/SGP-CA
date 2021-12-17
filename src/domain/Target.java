package domain;

public class Target{

    private String idTarget;
    private String description;
    private String title;
    private String status;
    private String keyWorkplan;

    public Target(String idTarget, String description, String title, String status, String keyWorkplan) {
        this.idTarget = idTarget;
        this.description = description;
        this.title = title;
        this.status = status;
        this.keyWorkplan = keyWorkplan;
    }

    public Target(String description, String title, String status, String keyWorkplan) {
        this.description = description;
        this.title = title;
        this.status = status;
        this.keyWorkplan = keyWorkplan;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeyWorkplan() {
        return keyWorkplan;
    }

    public void setKeyWorkplan(String keyWorkplan) {
        this.keyWorkplan = keyWorkplan;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Target.class){
            return false;
        }

        Target target = (Target) object;

        if(target.getIdTarget().equals(this.idTarget) &&
                target.getDescription().equals(this.description) &&
                target.getTitle().equals(this.title) &&
                target.getStatus().equals(this.status) &&
                target.getKeyWorkplan().equals(this.keyWorkplan)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Target{" +
                "idTarget='" + idTarget + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", keyWorkplan='" + keyWorkplan + '\'' +
                '}';
    }
}
