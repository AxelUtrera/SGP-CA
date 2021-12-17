package domain;

import java.util.Date;

public class PreliminaryProject {

    private String idPreliminaryProyect;
    private String title;
    private String description;
    private Date startDate;
    private String status;
    private String modality;
    private int duration;
    private String idInvestigationProject;

    public PreliminaryProject(String idPreliminaryProyect, String title, String description, Date startDate, String status, String modality,
                              int duration, String idInvestigationProject){
        this.idPreliminaryProyect = idPreliminaryProyect;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.status = status;
        this.modality = modality;
        this.duration = duration;
        this.idInvestigationProject = idInvestigationProject;
    }

    public PreliminaryProject(String title, String description, Date startDate, String status, String modality, int duration,
                              String idInvestigationProject) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.status = status;
        this.modality = modality;
        this.duration = duration;
        this.idInvestigationProject = idInvestigationProject;
    }

    public PreliminaryProject(String title, String description, Date startDate, String status, String modality, int duration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.status = status;
        this.modality = modality;
        this.duration = duration;
    }

    public String getIdPreliminaryProyect() {
        return idPreliminaryProyect;
    }

    public void setIdPreliminaryProyect(String idPreliminaryProyect) {
        this.idPreliminaryProyect = idPreliminaryProyect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIdInvestigationProject() {
        return idInvestigationProject;
    }

    public void setIdInvestigationProject(String idInvestigationProject) {
        this.idInvestigationProject = idInvestigationProject;
    }

    @Override
    public boolean equals(Object object) {

        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != PreliminaryProject.class){
            return false;
        }

        PreliminaryProject preliminaryProject = (PreliminaryProject) object;

        if(preliminaryProject.getIdPreliminaryProyect().equals(this.idPreliminaryProyect) &&
                preliminaryProject.getTitle().equals(this.title) &&
                preliminaryProject.getDescription().equals(this.description) &&
                preliminaryProject.getStartDate().equals(this.startDate) &&
                preliminaryProject.getStatus().equals(this.status) &&
                preliminaryProject.getModality().equals(this.modality) &&
                (preliminaryProject.getDuration() == this.duration) &&
                preliminaryProject.getIdInvestigationProject().equals(this.idInvestigationProject)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "PreliminaryProject{" +
                "idPreliminaryProyect='" + idPreliminaryProyect + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                ", modality='" + modality + '\'' +
                ", duration=" + duration +
                ", idInvestigationProject='" + idInvestigationProject + '\'' +
                '}';
    }
}
