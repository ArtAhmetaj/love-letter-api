package business.service;

import business.TO.mapper.CardMapper;
import business.TO.model.CardTO;
import business.persistency.repository.CardRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CardService {

    @Inject
    CardRepository cardRepository;

    @Inject
    CardMapper cardMapper;

    public CardTO getCardById(ObjectId cardId){
        var card = cardRepository.findById(cardId);
        return cardMapper.toResource(card);
    }

    public List<CardTO> getAllCards(){
        var cards = cardRepository.findAll();
        var listOfCards = cards.stream().collect(Collectors.toList());
        return listOfCards.stream().map(e->cardMapper.toResource(e)).collect(Collectors.toList());
    }


}
