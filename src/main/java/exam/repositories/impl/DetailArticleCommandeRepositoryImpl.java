package exam.repositories.impl;

import exam.entities.DetailArticleCommande;
import exam.repositories.DetailArticleCommandeRepository;
import exam.repositories.RepositoryImpl;

public class DetailArticleCommandeRepositoryImpl extends RepositoryImpl<DetailArticleCommande> implements DetailArticleCommandeRepository {

    public DetailArticleCommandeRepositoryImpl() {
        super(DetailArticleCommande.class);
    }
    
}
