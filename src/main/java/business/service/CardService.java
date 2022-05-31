package business.service;

import business.TO.mapper.CardMapper;
import business.TO.model.CardTO;
import business.persistency.entity.CardDAO;
import business.persistency.repository.impl.CardRepository;
import business.provider.impl.CacheProvider;
import com.arjuna.ats.jta.exceptions.NotImplementedException;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CardService {

    @Inject
    CardRepository cardRepository;

    @Inject
    CardMapper cardMapper;


    @Inject
    CacheProvider cacheProvider;

    public CardTO getCardById(ObjectId cardId) {
        CardDAO card;
        card = cacheProvider.get(cardId.toString(), CardDAO.class);
        if (card == null) card = cardRepository.findById(cardId);
        return cardMapper.toResource(card);
    }

    public List<CardTO> getAllCards() throws NotImplementedException {
        throw new NotImplementedException();
//        var cards = cardRepository.findAll();
//        var listOfCards = cards.stream().collect(Collectors.toList());
//        return listOfCards.stream().map(e->cardMapper.toResource(e)).collect(Collectors.toList());
    }


}
