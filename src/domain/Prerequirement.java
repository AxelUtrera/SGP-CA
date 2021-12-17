package domain;

public class Prerequirement {
    private String idPrerequirement;
    private String description;
    private String idMember;
    private String idMeet;

    public Prerequirement( String description, String idMember, String idMeet) {
        this.description = description;
        this.idMember = idMember;
        this.idMeet = idMeet;
    }

    public Prerequirement(String idPrerequirement, String description, String idMember, String idMeet) {
        this.idPrerequirement = idPrerequirement;
        this.description = description;
        this.idMember = idMember;
        this.idMeet = idMeet;
    }

    public String getIdPrerequirement() {
        return idPrerequirement;
    }

    public void setIdPrerequirement(String idPrerequirement) {
        this.idPrerequirement = idPrerequirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdMember() {
        return idMember;
    }

    public void setMember(String idMember) {
        this.idMember = idMember;
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
        if(object.getClass() != Prerequirement.class){
            return false;
        }
        Prerequirement prerequirement = (Prerequirement) object;

        if(prerequirement.getIdPrerequirement().equals(this.idPrerequirement)){
            return true;
        }else{
            return false;
        }
    }
}
