package exam.repositories.impl;

import exam.entities.Commande;
import exam.repositories.CommandeRepository;
import exam.repositories.RepositoryImpl;

public class CommandeRepositoryImpl extends RepositoryImpl<Commande> implements CommandeRepository {

    public CommandeRepositoryImpl() {
        super(Commande.class);
    }
    
}
