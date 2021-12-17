package testpackages.testbusinesslogic;

import businesslogic.BookDAO;
import businesslogic.BusinessLogicException;
import controller.AlertInterface;
import domain.Book;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BookTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllBooksSuccessfulTest(){ //FAIL

        BookDAO bookDAO = new BookDAO();
        List<Book> BooksExpected = new ArrayList<>();
        Book bookExpected1 = new Book("Book", "Libro1", "Maestria","IVP-1", "1234","LIS",2);
        Book bookExpected2 = new Book("Book", "Libro2", "Maestria","IVP-1", "5678","LIS",2);
        BooksExpected.add(bookExpected1);
        BooksExpected.add(bookExpected2);
        try{
            List<Book> BooksResult = bookDAO.displayAllBooks();

            assertEquals("Prueba obtener todas los libros existentes", BooksExpected, BooksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllBooksFailedTest(){

        BookDAO bookDAO = new BookDAO();

        List<Book> BooksExpected = new ArrayList<>();
        try{
            List<Book> BooksResult = bookDAO.displayAllBooks();

            assertEquals("Prueba alterna con lista vacia de libros", BooksExpected, BooksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addBooksuccessfulTest(){

        Book bookExpected = new Book("Book", "Libro1", "Maestria","IVP-1", "1234","LIS",2);
        BookDAO bookDAO = new BookDAO();
        try{
            boolean BooksAddedResult = bookDAO.addBook(bookExpected);
            assertTrue(BooksAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }
    @Test
    public void addBooksuccessfulTest2(){

        Book bookExpected = new Book("Book", "Libro2", "Maestria","IVP-1", "5678","LIS",2);;
        BookDAO bookDAO = new BookDAO();
        try{
            boolean BooksAddedResult = bookDAO.addBook(bookExpected);
            assertTrue(BooksAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateBookTest(){

        BookDAO bookDAO = new BookDAO();
        String idBook = "1234";
        try{
            Book book = bookDAO.foundBookByIsbn(idBook);
            book.setTitle("Contruccion2");
            boolean updatedBooksResult = bookDAO.updateBook(book);
            assertTrue(updatedBooksResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteBooksuccessfulTest(){

        String idBook = "5678";
        BookDAO bookDAO = new BookDAO();
        try{
            boolean BooksDeletedResult = bookDAO.deleteBook(idBook);
            assertTrue(BooksDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteBookFailedTest(){

        String idBook = "IVP-040";
        BookDAO bookDAO = new BookDAO();
        try{
            boolean BooksDeletedResult = bookDAO.deleteBook(idBook);
            assertTrue(BooksDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundBookByIDSuccessfulTest(){
        String idBook = "1234";
        BookDAO bookDAO = new BookDAO();
        Book bookExpected = new Book("Book", "Libro1", "Maestria","IVP-1", "1234","LIS",2);
        try{
            Book bookResult = bookDAO.foundBookByIsbn(idBook);
            assertEquals("Prueba verificar que un libro exista", bookExpected.getIsbn(), bookResult.getIsbn());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundBookByIDFailedTest(){
        String idBook = "IVP-065";
        BookDAO bookDAO = new BookDAO();
        try{
            assertNull("Prueba alterna, no existe el libro", bookDAO.foundBookByIsbn(idBook));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
