package domain;

public class Article extends Evidence{

    private String sici;
    private String magazineName;
    private String editorialMagazine;
    private String description;


    public Article(String type, String title, String academicdegree, String idInvestigationProject, String sici, String magazineName, String editorialMagazine, String description) {
        super(type, title, academicdegree, idInvestigationProject);
        this.sici = sici;
        this.magazineName = magazineName;
        this.editorialMagazine = editorialMagazine;
        this.description = description;
    }

    public String getSici() {
        return sici;
    }

    public void setSici(String sici) {
        this.sici = sici;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public String getEditorialMagazine() {
        return editorialMagazine;
    }

    public void setEditorialMagazine(String editorialMagazine) {
        this.editorialMagazine = editorialMagazine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSICI: " + "\nNombre de la revista: " + magazineName + "\nEditorial de la revista: " + editorialMagazine
                + "\nDescripción: " + description;
    }
    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  Article.class){
            return false;
        }

        Article article = (Article) object;

        if(article.getSici().equals(this.sici)){
            return true;
        }else{
            return false;
        }
    }
}
