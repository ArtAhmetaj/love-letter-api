package business.util;

import business.persistency.entity.CardDAO;
import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardUtils {
    private static final int MAX_CARDS = 32;

    private static int getRandomCardNumber(int currentCards){
        Random random = new Random();
        return random.nextInt(currentCards);
    }

    public static CardDAO giveCardToPlayer(List<CardDAO> allCards) {
        Collections.shuffle(allCards);
        return allCards.get(getRandomCardNumber(allCards.size()));
    }



}
