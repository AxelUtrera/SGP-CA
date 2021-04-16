package domain;

public class PreliminaryProyect{

    private String idPreliminaryProyect;
    private String title;
    private String description;
    private String startDate;
    private String status;
    private String modality;
    private String duration;
    private String lgac;

    public PreliminaryProyect(String idPreliminaryProyect, String title, String description, String startDate, String status, String modality, String duration, String lgac){
        this.idPreliminaryProyect = idPreliminaryProyect;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.status = status;
        this.modality = modality;
        this.duration = duration;
        this.lgac = lgac;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLgac() {
        return lgac;
    }

    public void setLgac(String lgac) {
        this.lgac = lgac;
    }

    @Override
    public String toString() {
        return "PreliminaryProyect{" +
                "idPreliminaryProyect='" + idPreliminaryProyect + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", status='" + status + '\'' +
                ", modality='" + modality + '\'' +
                ", duration='" + duration + '\'' +
                ", lgac='" + lgac + '\'' +
                '}';
    }
}
