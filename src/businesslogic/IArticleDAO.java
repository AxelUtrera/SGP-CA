package businesslogic;

import domain.Article;

import java.util.List;

public interface IArticleDAO {

    public List<Article> displayAllArticles() throws BusinessLogicException;
    public boolean addArticle(Article article) throws BusinessLogicException;
    public boolean updateArticle(Article article) throws BusinessLogicException;
    public boolean deleteArticle(String sici) throws BusinessLogicException;
    public Article foundArticleBySICI(String sici) throws BusinessLogicException;

}
