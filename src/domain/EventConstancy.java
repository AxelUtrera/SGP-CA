package domain;

public class EventConstancy {

    private String idEventConstancy;
    private String recognitionType;
    private String description;
    private String emailAssistant;
    private String idAcademicEvent;

    public EventConstancy(String idEventConstancy, String recognitionType, String description, String assistantEmail, String idAcademicEvent) {
        this.idEventConstancy = idEventConstancy;
        this.recognitionType = recognitionType;
        this.description = description;
        this.emailAssistant = assistantEmail;
        this.idAcademicEvent = idAcademicEvent;
    }

    public EventConstancy(String recognitionType, String description, String assistantEmail, String idAcademicEvent) {
        this.recognitionType = recognitionType;
        this.description = description;
        this.emailAssistant = assistantEmail;
        this.idAcademicEvent = idAcademicEvent;
    }

    public String getIdEventConstancy() {
        return idEventConstancy;
    }

    public void setIdEventConstancy(String idEventConstancy) {
        this.idEventConstancy = idEventConstancy;
    }

    public String getRecognitionType() {
        return recognitionType;
    }

    public void setRecognitionType(String recognitionType) {
        this.recognitionType = recognitionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailAssistant() {
        return emailAssistant;
    }

    public void setEmailAssistant(String emailAssistant) {
        this.emailAssistant = emailAssistant;
    }

    public String getIdAcademicEvent() {
        return idAcademicEvent;
    }

    public void setIdAcademicEvent(String idAcademicEvent) {
        this.idAcademicEvent = idAcademicEvent;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != EventConstancy.class){
            return false;
        }

        EventConstancy eventConstancy = (EventConstancy) object;

        if(eventConstancy.getIdEventConstancy().equals(this.idEventConstancy) &&
                eventConstancy.getRecognitionType().equals(this.recognitionType) &&
                eventConstancy.getDescription().equals(this.description) &&
                eventConstancy.getEmailAssistant().equals(this.emailAssistant) &&
                eventConstancy.getIdAcademicEvent().equals(this.idAcademicEvent)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "EventConstancy{" +
                "idEventConstancy='" + idEventConstancy + '\'' +
                ", recognitionType='" + recognitionType + '\'' +
                ", description='" + description + '\'' +
                ", emailAssistant='" + emailAssistant + '\'' +
                ", idAcademicEvent='" + idAcademicEvent + '\'' +
                '}';
    }
}
