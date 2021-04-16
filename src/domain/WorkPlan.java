package domain;

import java.util.Date;

public class WorkPlan {

    private String key;
    private Date starDate;
    private Date endDate;

    public WorkPlan(String key, Date starDate, Date endDate) {
        this.key = key;
        this.starDate = starDate;
        this.endDate = endDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getStarDate() {
        return starDate;
    }

    public void setStarDate(Date starDate) {
        this.starDate = starDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Strategy.class){
            return false;
        }

        WorkPlan workPlan = (WorkPlan) object;

        if(workPlan.getKey().equals(this.getKey())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "WorkPlan{" +
                "key='" + key + '\'' +
                ", starDate='" + starDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
