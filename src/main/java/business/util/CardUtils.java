package business.util;

import business.persistency.entity.CardDAO;
import com.arjuna.ats.jta.exceptions.NotImplementedException;

import java.util.List;
import java.util.Random;

public class CardUtils {
    private static final int MAX_CARDS = 32;

    private static int getRandomCardNumber(){
        Random random = new Random();
        return random.nextInt(MAX_CARDS);
    }

    public static List<CardDAO> shuffleCards(List<CardDAO> allCards) throws NotImplementedException {
        throw  new NotImplementedException();
    }

}
