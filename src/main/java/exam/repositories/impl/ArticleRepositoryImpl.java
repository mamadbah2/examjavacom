package exam.repositories.impl;

import exam.entities.Article;
import exam.repositories.ArticleRepository;
import exam.repositories.RepositoryImpl;

public class ArticleRepositoryImpl extends RepositoryImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImpl() {
        super(Article.class);
    }
    @Override
    public Article selectByLibelle(String libelle) {
        return entityManager.find(entityClass, libelle);
    }
    
}
