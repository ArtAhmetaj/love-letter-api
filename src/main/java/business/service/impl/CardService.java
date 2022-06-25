package business.service.impl;

import business.TO.mapper.CardMapper;
import business.TO.model.CardTO;
import business.persistency.cache.CachedCardRepository;
import com.arjuna.ats.jta.exceptions.NotImplementedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CardService {

   @Inject
    CachedCardRepository cardRepository;

    @Inject
    CardMapper cardMapper;

    public List<CardTO> getAllCards(){
        var cards = cardRepository.findAllCards();
        return cards.stream().map(e->cardMapper.toResource(e)).collect(Collectors.toList());
    }


}
