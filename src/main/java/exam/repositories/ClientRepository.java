package exam.repositories;

import exam.core.Repository;
import exam.entities.Client;

public interface ClientRepository  extends Repository<Client> {
    Client selectByTel(String tel);
    Client selectBySurname(String surname);
}
