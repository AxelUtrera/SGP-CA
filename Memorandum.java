package domain;

import java.sql.Time;
import java.util.Date;

public class Memorandum {
    private String idMemorandum;
    private Time hour;
    private String place;
    private Date date;
    private String subject;

    public Memorandum(String idMemorandum, Time hour, String place, Date date, String subject) {
        this.idMemorandum = idMemorandum;
        this.hour = hour;
        this.place = place;
        this.date = date;
        this.subject = subject;
    }
    public String getIdMemorandum(){
        return idMemorandum;
    }
    public void setIdMemorandum(String idMemorandum){
        this.idMemorandum = idMemorandum;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
