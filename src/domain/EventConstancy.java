package domain;

public class EventConstancy {

    private String idEventConstancy;
    private String recognitionType;
    private String reglamentoryNotes;
    private String description;
    private String assistantEmail;

    public EventConstancy(String idEventConstancy, String recognitionType, String reglamentoryNotes, String description, String assistantEmail) {
        this.idEventConstancy = idEventConstancy;
        this.recognitionType = recognitionType;
        this.reglamentoryNotes = reglamentoryNotes;
        this.description = description;
        this.assistantEmail = assistantEmail;
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

    public String getReglamentoryNotes() {
        return reglamentoryNotes;
    }

    public void setReglamentoryNotes(String reglamentoryNotes) {
        this.reglamentoryNotes = reglamentoryNotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssistantEmail() {
        return assistantEmail;
    }

    public void setAssistantEmail(String assistantEmail) {
        this.assistantEmail = assistantEmail;
    }

    @Override
    public String toString() {
        return "EventConstancy{" +
                "idEventConstancy='" + idEventConstancy + '\'' +
                ", recognitionType='" + recognitionType + '\'' +
                ", reglamentoryNotes='" + reglamentoryNotes + '\'' +
                ", description='" + description + '\'' +
                ", assistantEmail='" + assistantEmail + '\'' +
                '}';
    }
}
