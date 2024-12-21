package exam.repositories;

import exam.core.Repository;
import exam.entities.Article;

public interface ArticleRepository extends Repository<Article> {
    Article selectByLibelle(String libelle);
    Article selectById(int id);
} 