package businesslogic;

import domain.Book;

import java.util.List;

public interface IBookDAO {

    public List<Book> displayAllBooks() throws BusinessLogicException;
    public boolean addBook(Book book) throws BusinessLogicException;
    public boolean updateBook(Book book) throws BusinessLogicException;
    public boolean deleteBook(String isbn) throws BusinessLogicException;
    public Book foundBookByIsbn(String isbn) throws BusinessLogicException;

}
