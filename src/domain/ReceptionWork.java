package domain;

public class ReceptionWork extends Evidence{

    private String idReceptionWork;
    private String condition;
    private String studentName;


    public ReceptionWork(String type, String title, String academicdegree, String idInvestigationProject, String idReceptionWork, String condition, String studentName) {
        super(type, title, academicdegree, idInvestigationProject);
        this.idReceptionWork = idReceptionWork;
        this.condition = condition;
        this.studentName = studentName;
    }

    public ReceptionWork(String type, String title, String academicdegree, String idInvestigationProject,  String condition, String studentName) {
        super(type, title, academicdegree, idInvestigationProject);
        this.condition = condition;
        this.studentName = studentName;
    }


    public String getIdReceptionWork() {
        return idReceptionWork;
    }

    public void setIdReceptionWork(String idReceptionWork) {
        this.idReceptionWork = idReceptionWork;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Trabajo recepcional " +idReceptionWork +
                "\nCondición: "+ condition +
                "\nNombre del estudiante: "+ studentName;
    }
    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  ReceptionWork.class){
            return false;
        }

        ReceptionWork receptionWork = (ReceptionWork) object;

        if(receptionWork.getIdReceptionWork().equals(this.idReceptionWork)){
            return true;
        }else{
            return false;
        }
    }

}
