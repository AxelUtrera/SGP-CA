package domain;

import businesslogic.BusinessLogicException;
import businesslogic.InvestigationProjectDAO;
import controller.AlertInterface;

public class Evidence {

    protected String type;
    protected String title;
    protected String academicdegree;
    protected String idInvestigationProject;

    public Evidence(String type, String title, String academicdegree, String idInvestigationProject) {
        this.type = type;
        this.title = title;
        this.academicdegree = academicdegree;
        this.idInvestigationProject = idInvestigationProject;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAcademicdegree() {

        return academicdegree;
    }

    public void setAcademicdegree(String academicdegree) {

        this.academicdegree = academicdegree;
    }

    public String getIdInvestigationProject() {

        return idInvestigationProject;
    }

    public void setIdInvestigationProject(String idInvestigationProject) {
        this.idInvestigationProject = idInvestigationProject;
    }

    @Override
    public String toString() {
        AlertInterface alertInterface = new AlertInterface();
        InvestigationProjectDAO investigationProjectDAO = new InvestigationProjectDAO();
        InvestigationProject investigationProject = null;
        try{
            investigationProject = investigationProjectDAO.foundInvestigationProjectByidInvestigationProject(idInvestigationProject);

        } catch (BusinessLogicException ex) {
            alertInterface.openAlertFailedInsert();
        }

        return type + "\nTitulo: " + title+ "\nGrado Academico: "+ academicdegree+
                "\n\nProyecto de investigación relacionado: " + "\n"+ investigationProject.toString() + "\n\n";
    }
}
