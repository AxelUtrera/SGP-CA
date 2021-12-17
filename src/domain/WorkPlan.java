package domain;

import java.util.Date;

public class WorkPlan {

    private String keyWorkPlan;
    private Date startDate;
    private Date endDate;
    private String curpMember;
    private String keyAcademicGroup;

    public WorkPlan(String keyWorkPlan, Date startDate, Date endDate, String keyAcademicGroup, String curpMember) {
        this.keyWorkPlan = keyWorkPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyAcademicGroup = keyAcademicGroup;
        this.curpMember = curpMember;
    }

    public WorkPlan(Date startDate, Date endDate, String keyAcademicGroup, String curpMember) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyAcademicGroup = keyAcademicGroup;
        this.curpMember = curpMember;
    }

    public String getKeyWorkPlan() {
        return keyWorkPlan;
    }

    public void setKeyWorkPlan(String keyWorkPlan) {
        this.keyWorkPlan = keyWorkPlan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getKeyAcademicGroup() {
        return keyAcademicGroup;
    }

    public void setKeyAcademicGroup(String keyAcademicGroup) {
        this.keyAcademicGroup = keyAcademicGroup;
    }

    public String getCurpMember() {
        return curpMember;
    }

    public void setCurpMember(String curpMember) {
        this.curpMember = curpMember;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != WorkPlan.class){
            return false;
        }

        WorkPlan workPlan = (WorkPlan) object;

        if(workPlan.getKeyWorkPlan().equals(this.keyWorkPlan) &&
                workPlan.getStartDate().equals(this.startDate) &&
                workPlan.getEndDate().equals(this.endDate) &&
                workPlan.getKeyAcademicGroup().equals(this.keyAcademicGroup) &&
                workPlan.getCurpMember().equals(this.curpMember)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "WorkPlan{" +
                "keyWorkPlan='" + keyWorkPlan + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", curpMember='" + curpMember + '\'' +
                ", keyAcademicGroup='" + keyAcademicGroup + '\'' +
                '}';
    }
}
