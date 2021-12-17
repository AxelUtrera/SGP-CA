package domain;

public class EventAssistant {

    private String email;
    private String name;

    public EventAssistant(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != EventAssistant.class){
            return false;
        }

        EventAssistant eventAssistant = (EventAssistant) object;

        if(eventAssistant.getEmail().equals(this.email) &&
                eventAssistant.getName().equals(this.name)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "EventAssistant{" +
                "eMail='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
