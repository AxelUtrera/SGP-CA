package businesslogic;

import dataaccess.Connector;
import domain.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO implements IBookDAO{

    private final Connector CONNECTOR = new Connector();

    public List<Book> displayAllBooks() throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM book";
        Connection connection = null;
        Book book;
        List<Book> bookList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String editorial = resultSet.getString("editorial");
                int editionNumber = resultSet.getInt("editionNumber");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                book = new Book (type, title, academicDegree, idInvestigationProject, isbn, editorial, editionNumber);
                bookList.add(book);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return bookList;
    }

    public boolean addBook(Book book) throws BusinessLogicException{


        final String SQL_INSERT = "INSERT INTO book (`isbn`, `editorial`, `editionNumber`, `type`, `title`, `academicDegree`, `idInvestigationProject`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getEditorial());
            statement.setInt( 3, book.getEditionNumber());
            statement.setString(4, book.getType());
            statement.setString(5, book.getTitle());
            statement.setString(6, book.getAcademicdegree());
            statement.setString(7, book.getIdInvestigationProject());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }


    public boolean updateBook(Book book) throws BusinessLogicException{


        final String SQL_UPDATE = "UPDATE book SET isbn=?, editorial=?, editionNumber=?, `type`=?, title=?, academicDegree=?, idInvestigationProject=?  WHERE isbn=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getEditorial());
            statement.setInt( 3, book.getEditionNumber());
            statement.setString(4, book.getType());
            statement.setString(5, book.getTitle());
            statement.setString(6, book.getAcademicdegree());
            statement.setString(7, book.getIdInvestigationProject());
            statement.setString(8, book.getIsbn());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteBook(String isbn) throws BusinessLogicException{


        final String SQL_DELETE = "DELETE FROM book WHERE isbn=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, isbn);
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public Book foundBookByIsbn(String ISBN) throws BusinessLogicException{


        final String SQL_SELECT = "SELECT * FROM book WHERE isbn = ?";
        Connection connection = null;
        Book book = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, ISBN);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String editorial = resultSet.getString("editorial");
                int editionNumber = resultSet.getInt("editionNumber");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                book = new Book(type, title, academicDegree, idInvestigationProject, isbn, editorial, editionNumber);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return book;
    }



}




