package domain;

public class Prerequirement {
    private String member;
    private String description;

    public Prerequirement(String member, String description) {
        this.member = member;
        this.description = description;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
