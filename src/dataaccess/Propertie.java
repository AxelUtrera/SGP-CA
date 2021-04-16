package dataaccess;

public class Propertie{

    String URL;
    String USER;
    String PASSWORD;

    public Propertie(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public String getURL() {
        return URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(object.getClass() != Propertie.class){
            return false;
        }

        Propertie propertie = (Propertie) object;

        if(propertie.getURL().equals(this.getURL()) && (propertie.getUSER().equals(this.getUSER())) && (propertie.getPASSWORD().equals(this.getPASSWORD()))){
            return true;
        }else{
            return false;
        }
    }
}
