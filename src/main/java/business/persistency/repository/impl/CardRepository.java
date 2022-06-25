package business.persistency.repository.impl;

import business.persistency.entity.CardDAO;
import business.persistency.repository.ICardRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CardRepository implements PanacheMongoRepository<CardDAO>, ICardRepository {


    @Override
    public List<CardDAO> findAllCards() {
       return  findAll().list();
    }
}
