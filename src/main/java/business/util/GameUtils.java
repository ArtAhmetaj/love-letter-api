package business.util;

import business.persistency.entity.GameDAO;
import business.persistency.entity.RoundDAO;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class GameUtils {


    public static GameDAO completeRound(GameDAO game){
        var lastRound  = game.rounds.get(game.rounds.size()-1);
        lastRound.completedDate = LocalDateTime.now();
        lastRound.winner = game.players.get(0);
        game.players.addAll(game.eliminatedPlayers);
        game.eliminatedPlayers.clear();
        //TODO: add builder
        var newRound = new RoundDAO();
        newRound.createdDate = LocalDateTime.now();
        newRound.currentActivePlayer = game.players.get(0);

        game.rounds.add(newRound);
        return game;
    }

}
