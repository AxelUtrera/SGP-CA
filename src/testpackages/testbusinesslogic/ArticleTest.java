package testpackages.testbusinesslogic;

import businesslogic.ArticleDAO;
import businesslogic.BusinessLogicException;
import controller.AlertInterface;
import domain.Article;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ArticleTest {

    private final AlertInterface ALERT_INTERFACE = new AlertInterface();

    @Test
    public void displayAllArticlesSuccessfulTest(){

        ArticleDAO articleDAO = new ArticleDAO();
        List<Article> ArticlesExpected = new ArrayList<>();
        Article articleExpected1 = new Article("Article","Articulo1","Doctorado","IVP-1","1234","Magazine","LIS","Magazine of lis");
        Article articleExpected2 = new Article("Article","Articulo1","Doctorado","IVP-1","5678","Magazine","LIS","Magazine of lis");;
        ArticlesExpected.add(articleExpected1);
        ArticlesExpected.add(articleExpected2);
        try{
            List<Article> ArticlesResult = articleDAO.displayAllArticles();

            assertEquals("Prueba obtener todas los articulos existentes", ArticlesExpected, ArticlesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void displayAllArticlesFailedTest(){

        ArticleDAO articleDAO = new ArticleDAO();

        List<Article> ArticlesExpected = new ArrayList<>();
        try{
            List<Article> ArticlesResult = articleDAO.displayAllArticles();

            assertEquals("Prueba alterna con lista vacia de articulos", ArticlesExpected, ArticlesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void addArticlesuccessfulTest(){

        Article articleExpected = new Article("Article","Articulo1","Doctorado","IVP-1","1234","Magazine","LIS","Magazine of lis");
        ArticleDAO articleDAO = new ArticleDAO();
        try{
            boolean ArticlesAddedResult = articleDAO.addArticle(articleExpected);
            assertTrue(ArticlesAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }
    @Test
    public void addArticlesuccessfulTest2(){

        Article articleExpected = new Article("Article","Articulo1","Doctorado","IVP-1","5678","Magazine","LIS","Magazine of lis");
        ArticleDAO articleDAO = new ArticleDAO();
        try{
            boolean ArticlesAddedResult = articleDAO.addArticle(articleExpected);
            assertTrue(ArticlesAddedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void updateArticleTest() {

        ArticleDAO articleDAO = new ArticleDAO();
        String idArticle = "5678";
        try{
            Article article = articleDAO.foundArticleBySICI(idArticle);
            article.setTitle("Contruccion2");
            boolean updatedArticlesResult = articleDAO.updateArticle(article);
            assertTrue(updatedArticlesResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteArticlesuccessfulTest(){

        String idArticle = "1234";
        ArticleDAO articleDAO = new ArticleDAO();
        try{
            boolean ArticlesDeletedResult = articleDAO.deleteArticle(idArticle);
            assertTrue(ArticlesDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void deleteArticleFailedTest(){

        String idArticle = "IVP-040";
        ArticleDAO articleDAO = new ArticleDAO();
        try{
            boolean ArticlesDeletedResult = articleDAO.deleteArticle(idArticle);
            assertTrue(ArticlesDeletedResult);
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }

    }

    @Test
    public void foundArticleByIDSuccessfulTest(){
        String idArticle = "1234";
        ArticleDAO articleDAO = new ArticleDAO();
        Article articleExpected = new Article("Article","Articulo1","Doctorado","IVP-1","1234","Magazine","LIS","Magazine of lis");
        try{
            Article articleResult = articleDAO.foundArticleBySICI(idArticle);
            assertEquals("Prueba verificar que un articulo exista", articleExpected.getSici(), articleResult.getSici());
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }

    @Test
    public void foundArticleByIDFailedTest(){
        String idArticle = "IVP-065";
        ArticleDAO articleDAO = new ArticleDAO();
        try{
            assertNull("Prueba alterna, no existe el articulo", articleDAO.foundArticleBySICI(idArticle));
        } catch (BusinessLogicException ex) {
            ALERT_INTERFACE.openAlertFailedInsert();
        }
    }



}
