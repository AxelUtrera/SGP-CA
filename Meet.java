package domain;

import java.sql.Date;
import java.sql.Time;

public class Meet {
    private String idMeet;
    private Date dateMeet;
    private String subject;
    private Time hourMeet;
    private String proyectName;
    private String placeMeet;

    public Meet(String idMeet, Date dateMeet, String subject, Time hourMeet, String proyectName, String placeMeet) {
        this.idMeet = idMeet;
        this.dateMeet = dateMeet;
        this.subject = subject;
        this.hourMeet = hourMeet;
        this.proyectName = proyectName;
        this.placeMeet = placeMeet;
    }

    public String getIdMeet() {
        return idMeet;
    }

    public void setIdMeet(String idMeet) {
        this.idMeet = idMeet;
    }

    public Date getDateMeet() {
        return dateMeet;
    }

    public void setDateMeet(Date dateMeet) {
        this.dateMeet = dateMeet;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Time getHourMeet() {
        return hourMeet;
    }

    public void setHourMeet(Time hourMeet) {
        this.hourMeet = hourMeet;
    }

    public String getProyectName() {
        return proyectName;
    }

    public void setProyectName(String proyectName) {
        this.proyectName = proyectName;
    }

    public String getPlaceMeet() {
        return placeMeet;
    }

    public void setPlaceMeet(String placeMeet) {
        this.placeMeet = placeMeet;
    }

}
