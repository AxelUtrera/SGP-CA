package domain;

public class Target{

    private String idTarget;
    private String descripcion;
    private String title;

    public Target(String idTarget, String descripcion, String title) {
        this.idTarget = idTarget;
        this.descripcion = descripcion;
        this.title = title;
    }

    public String getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(String idTarget) {
        this.idTarget = idTarget;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        Target target = (Target) object;

        if(target.getIdTarget().equals(this.idTarget)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "Target{" +
                "idTarget='" + idTarget + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
