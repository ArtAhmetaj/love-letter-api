package business.validator;

import business.TO.model.GameTO;
import business.exceptions.models.business.PlayerNotPartOfGameException;
import org.bson.types.ObjectId;

public class GameValidator {

    public static void arePlayersPartOfGame(GameTO game, ObjectId ...players){
        for(var player : players){
            if(!game.players.contains(player)) throw new PlayerNotPartOfGameException();
        }
    }
}
