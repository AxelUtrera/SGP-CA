package domain;

import java.sql.Date;
import java.sql.Time;

public class Event {
    private String idAcademicEvent;
    private String title;
    private String type;
    private Time hour;
    private String place;
    private Date eventDate;

    public Event(String title, String type, Time hour, String place, Date eventDate) {
        this.title = title;
        this.type = type;
        this.hour = hour;
        this.place = place;
        this.eventDate = eventDate;
    }

    public Event(String idAcademicEvent, String title, String type, Time hour, String place, Date eventDate) {
        this.idAcademicEvent = idAcademicEvent;
        this.title = title;
        this.type = type;
        this.hour = hour;
        this.place = place;
        this.eventDate = eventDate;
    }


    public String getIdAcademicEvent() {
        return idAcademicEvent;
    }

    public void setIdAcademicEvent(String idAcademicEvent) {
        this.idAcademicEvent = idAcademicEvent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Event.class){
            return false;
        }

        Event event = (Event) object;
        if(event.getIdAcademicEvent().equals(this.idAcademicEvent)){
            return true;
        }else{
            return false;
        }
    }
}
