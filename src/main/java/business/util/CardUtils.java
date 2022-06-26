package business.util;

import business.persistency.entity.CardDAO;
import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.util.*;

public class CardUtils {
    private static final int MAX_CARDS = 32;

    private static int getRandomCardNumber(int currentCards) {
        Random random = new Random();
        return random.nextInt(currentCards);
    }

    public static CardDAO giveCardToPlayer(List<CardDAO> allCards) {
        Collections.shuffle(allCards);
        return allCards.get(getRandomCardNumber(allCards.size()));
    }


    public static List<CardDAO> getCardsForNewRound(List<CardDAO> allCards, int numberOfPlayers) {
        var cards = new HashSet<CardDAO>();
        // set makes sure we dont get any duplicates, looks a bit cleaner
        while (cards.size() != numberOfPlayers) {
            cards.add(allCards.get(getRandomCardNumber(allCards.size())));
        }

        return new ArrayList<>(cards);

    }


}
