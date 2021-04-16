package domain;

public class EventAssistant {

    private String eMail;
    private String name;

    public EventAssistant(String eMail, String name) {
        this.eMail = eMail;
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventAssistant{" +
                "eMail='" + eMail + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
