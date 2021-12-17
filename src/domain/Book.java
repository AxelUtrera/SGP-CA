package domain;

public class Book extends Evidence{

    private String isbn;
    private String editorial;
    private int editionNumber;

    public Book(String type, String title, String academicdegree, String idInvestigationProject, String isbn, String editorial, int editionNumber) {
        super(type, title, academicdegree, idInvestigationProject);
        this.isbn = isbn;
        this.editorial = editorial;
        this.editionNumber = editionNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return super.toString()+ "ISBN: " + isbn + "\nEditorial: " + editorial +
                "\nNumero de edición: " + editionNumber;
    }

    @Override
    public boolean equals (Object object){
        if(this == object){
            return true;
        }
        if(this == null){
            return false;
        }

        if(object.getClass() !=  Book.class){
            return false;
        }

        Book book = (Book) object;

        if(book.getIsbn().equals(this.isbn)){
            return true;
        }else{
            return false;
        }
    }
}
