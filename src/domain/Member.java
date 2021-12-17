package domain;

import java.util.Date;

public class Member {

    private String curp;
    private String name;
    private Date dateofBirth;
    private String institutionalMail;
    private String telephoneNumber;
    private String academicRole;
    private String keyAcademicGroup;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Member(String curp, String name, Date dateofBirth, String institutionalMail, String telephoneNumber, String academicRole, String keyAcademicGroup, String password) {
        this.curp = curp;
        this.name = name;
        this.dateofBirth = dateofBirth;
        this.institutionalMail = institutionalMail;
        this.telephoneNumber = telephoneNumber;
        this.academicRole = academicRole;
        this.keyAcademicGroup = keyAcademicGroup;
        this.password = password;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getDateofBirth() {
        return (java.sql.Date) dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getInstitutionalMail() {
        return institutionalMail;
    }

    public void setInstitutionalMail(String institutionalMail) {
        this.institutionalMail = institutionalMail;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAcademicRole() {
        return academicRole;
    }

    public void setAcademicRole(String academicRole) {
        this.academicRole = academicRole;
    }

    public String getKeyAcademicGroup() {
        return keyAcademicGroup;
    }

    public void setKeyAcademicGroup(String keyAcademicGroup) {
        this.keyAcademicGroup = keyAcademicGroup;
    }

    @Override
    public String toString() {
        return "Member{" +
                "CURP='" + curp + '\'' +
                ", name='" + name + '\'' +
                ", dateofBirth=" + dateofBirth +
                ", institutionalMail='" + institutionalMail + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", academicRole='" + academicRole + '\'' +
                ", keyAcademicGroup='" + keyAcademicGroup + '\'' +
                '}';
    }

    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  Member.class){
            return false;
        }

        Member member = (Member) object;

        if(member.getCurp().equals(this.curp)){
            return true;
        }else{
            return false;
        }
    }
}
