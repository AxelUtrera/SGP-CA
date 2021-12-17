package domain;

public class Prototype extends Evidence {

    private String idPrototype;

    public Prototype(String type, String title, String academicdegree, String idInvestigationProject, String idPrototype) {
        super(type, title, academicdegree, idInvestigationProject);
        this.idPrototype = idPrototype;
    }

    public Prototype(String type, String title, String academicdegree, String idInvestigationProject) {
        super(type, title, academicdegree, idInvestigationProject);

    }

    public String getIdPrototype() {

        return idPrototype;
    }

    public void setIdPrototype(String idPrototype) {

        this.idPrototype = idPrototype;
    }

    @Override
    public String toString() {
        return "Prototipo " + idPrototype;
    }


    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  Prototype.class){
            return false;
        }

        Prototype prototype = (Prototype) object;

        if(prototype.getIdPrototype().equals(this.idPrototype)){
            return true;
        }else{
            return false;
        }
    }
}
