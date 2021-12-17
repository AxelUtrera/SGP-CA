package domain;

import java.sql.Date;

public class InvestigationProject {

    private String idInvestigationProject;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;

    public InvestigationProject(String idInvestigationProject, String title, Date startDate, Date endDate, String description) {
        this.idInvestigationProject = idInvestigationProject;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public InvestigationProject(String title, Date startDate, Date endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getIdInvestigationProject() {
        return idInvestigationProject;
    }

    public void setIdInvestigationProject(String idInvestigationProject) {
        this.idInvestigationProject = idInvestigationProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Proyecto de investigación " + title + '\n' +
                "ID del proyecto: " + idInvestigationProject + '\n' +
                "Fecha de inicio: " + startDate + '\n' +
                "Fecha de inicio: " + endDate + '\n' +
                "Descripción: " + description + '\n';
    }

    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  InvestigationProject.class){
            return false;
        }

        InvestigationProject investigationProject = (InvestigationProject) object;

        if(investigationProject.getIdInvestigationProject().equals(this.idInvestigationProject)){
            return true;
        }else{
            return false;
        }
    }
}
