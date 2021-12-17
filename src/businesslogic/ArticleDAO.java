package businesslogic;

import dataaccess.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Article;

public class ArticleDAO implements IArticleDAO{

    private final Connector CONNECTOR = new Connector();

    public List<Article> displayAllArticles() throws BusinessLogicException{

        final String SQL_SELECT = "SELECT * FROM article";
        Connection connection = null;
        Article article;
        List<Article> articleList = new ArrayList<>();

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String sici = resultSet.getString("sici");
                String magazineName = resultSet.getString("magazineName");
                String editorialMagazine = resultSet.getString("editorialMagazine");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                article = new Article (type, title, academicDegree, idInvestigationProject, sici, magazineName, editorialMagazine, description);
                articleList.add(article);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return articleList;
    }

    public boolean addArticle(Article article) throws BusinessLogicException{

        final String SQL_INSERT = "INSERT INTO article (`sici`, `magazineName`, `editorialMagazine`, `description`, `type`, `title`, `academicDegree`, `idInvestigationProject`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, article.getSici());
            statement.setString(2, article.getMagazineName());
            statement.setString(3, article.getEditorialMagazine());
            statement.setString(4, article.getDescription());
            statement.setString(5, article.getType());
            statement.setString(6, article.getTitle());
            statement.setString(7, article.getAcademicdegree());
            statement.setString(8, article.getIdInvestigationProject());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }


    public boolean updateArticle(Article article) throws BusinessLogicException{

        final String SQL_UPDATE = "UPDATE article SET sici=?, magazineName=?, editorialMagazine=?, description=?, type=?, title=?, academicDegree=?,idInvestigationProject=?  WHERE sici=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, article.getSici());
            statement.setString(2, article.getMagazineName());
            statement.setString(3, article.getEditorialMagazine());
            statement.setString(4, article.getDescription());
            statement.setString(5, article.getType());
            statement.setString(6, article.getTitle());
            statement.setString(7, article.getAcademicdegree());
            statement.setString(8, article.getIdInvestigationProject());
            statement.setString(9, article.getSici());
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;

    }

    public boolean deleteArticle(String SICI) throws BusinessLogicException{

        String SQL_DELETE = "DELETE FROM article WHERE sici=?";
        Connection connection = null;
        boolean operationResult = false;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setString(1, SICI);
            statement.executeUpdate();
            operationResult = true;

        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        }finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return operationResult;
    }

    public Article foundArticleBySICI(String SICI) throws BusinessLogicException{

        String SQL_SELECT = "SELECT * FROM article WHERE sici = ?";
        Connection connection = null;
        Article article = null;

        try {

            connection = CONNECTOR.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT);
            statement.setString(1, SICI);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String sici = resultSet.getString("sici");
                String magazineName = resultSet.getString("magazineName");
                String editorialMagazine = resultSet.getString("editorialMagazine");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");
                String title = resultSet.getString("title");
                String academicDegree = resultSet.getString("academicDegree");
                String idInvestigationProject = resultSet.getString("idInvestigationProject");

                article = new Article(type, title, academicDegree, idInvestigationProject, sici, magazineName, editorialMagazine, description);
            }
        } catch (SQLException sQLException) {
            throw new BusinessLogicException("ConnectionException", sQLException);
        } finally {
            try {
                CONNECTOR.closeConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(ArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return article;
    }

}


