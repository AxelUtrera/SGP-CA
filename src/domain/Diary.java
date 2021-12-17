package domain;

public class Diary {
    private int number;
    private String discussionLeader;
    private int realTime;
    private int estimatedTime;
    private String pointDiscussed;
    private String idMeet;

    public Diary(int number, String discussionLeader, int realTime, int estimatedTime, String pointDiscussed, String idMeet) {
        this.number = number;
        this.discussionLeader = discussionLeader;
        this.realTime = realTime;
        this.estimatedTime = estimatedTime;
        this.pointDiscussed = pointDiscussed;
        this.idMeet = idMeet;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDiscussionLeader() {
        return discussionLeader;
    }

    public void setDiscussionLeader(String discussionLeader) {
        this.discussionLeader = discussionLeader;
    }

    public int getRealTime() {
        return realTime;
    }

    public void setRealTime(int realTime) {
        this.realTime = realTime;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPointDiscussed() {
        return pointDiscussed;
    }

    public void setPontDiscussed(String pointDiscussed) {
        this.pointDiscussed = pointDiscussed;
    }

    public String getIdMeet() {
        return idMeet;
    }

    public void setIdMeet(String idMeet) {
        this.idMeet = idMeet;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Diary.class){
            return false;
        }
        Diary diary = (Diary) object;

        if(diary.getIdMeet().equals(this.idMeet)){
            return true;
        }else{
            return false;
        }
    }
}
