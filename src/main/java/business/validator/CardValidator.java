package business.validator;

import business.TO.model.CardNameTO;
import business.TO.model.CardTO;
import business.exceptions.business.CardNotFoundException;
import business.exceptions.business.MustThrowCountessException;

import java.util.List;

public class CardValidator {

    private static final List<CardNameTO> countessAndKing = List.of(CardNameTO.COUNTESS,CardNameTO.KING);
    public static void cardBelongsToUser(List<CardTO> cards , CardNameTO cardName){
        if(cards.stream().noneMatch(e->e.cardName == cardName)) throw new CardNotFoundException();
    }

    public static void mustDiscardCountessIfKingPresent(List<CardTO> cards,CardTO card){
        //TODO: unreadable lamba, simplify later, magic '2'
        var hasConditionPresent = cards.stream().filter(e->countessAndKing.contains(e.cardName)).count()==2;

        if(hasConditionPresent && card.cardName != CardNameTO.COUNTESS) throw new MustThrowCountessException();

    }

}
