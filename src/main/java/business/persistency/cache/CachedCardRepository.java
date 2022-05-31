package business.persistency.cache;

import business.persistency.entity.CardDAO;
import business.persistency.repository.ICardRepository;
import business.persistency.repository.impl.CardRepository;
import business.provider.impl.CacheProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
@ApplicationScoped
public class CachedCardRepository implements ICardRepository {

    @Inject
    CacheProvider cacheProvider;


    @Inject
    CardRepository cardRepository;

    @Override
    public List<CardDAO> findAllCards() {
        var response = cacheProvider.getCollection(CacheConstants.CARDS,CardDAO.class);
        if(response !=null) return response;
        return cardRepository.findAllCards();

    }
}
