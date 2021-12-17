package domain;

public class AcademicGroup{

    private String key;
    private String name;
    private String responsible;
    private String degreeConsolidation;


    public AcademicGroup(String key, String name, String responsible, String degreeConsolidation) {
        this.key = key;
        this.name = name;
        this.responsible = responsible;
        this.degreeConsolidation = degreeConsolidation;
    }

    public String getKey() {

        return key;
    }

    public String getName() {

        return name;
    }

    public String getResponsible() {

        return responsible;
    }

    public String getDegreeConsolidation() {

        return degreeConsolidation;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setResponsible(String responsible) {

        this.responsible = responsible;
    }

    public void setDegreeConsolidation(String degreeConsolidation) {

        this.degreeConsolidation = degreeConsolidation;
    }


    @Override
    public String toString() {
        return "AcademicGroup{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", responsible='" + responsible + '\'' +
                ", degreeConsolidation='" + degreeConsolidation + '\'' +
                '}';
    }

    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  AcademicGroup.class){
            return false;
        }

        AcademicGroup academicGroup = (AcademicGroup) object;

        if(academicGroup.getKey().equals(this.key)){
            return true;
        }else{
            return false;
        }
    }
}
