package business.service.impl;

import business.TO.mapper.GameMapper;
import business.TO.model.CardTO;
import business.TO.model.GameTO;
import business.TO.model.UserTO;
import business.exceptions.HandMaidCanNotBeAttackedException;
import business.persistency.cache.CachedGameRepository;
import business.persistency.cache.CachedUserRepository;
import business.persistency.entity.UserDAO;
import business.persistency.repository.impl.UserRepository;
import business.util.CardUtils;
import business.util.GameUtils;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameService {

    @Inject
    CachedGameRepository gameRepository;

    @Inject
    CachedUserRepository userRepository;

    @Inject
    GameMapper gameMapper;


    public GameTO getGameById(ObjectId id) {
        var gameDAO = gameRepository.findGameById(id);
        return gameMapper.toResource(gameDAO);
    }

    public GameTO createGame(GameTO game){
        var gameEntity = gameMapper.fromResource(game);
        gameRepository.saveGame(gameEntity);
        return  gameMapper.toResource(gameEntity);
    }

    public GameTO addPlayerToGame(UserTO user, ObjectId gameId) {
        var game = gameRepository.findGameById(gameId);
        game.players.add(user.id);
        gameRepository.saveGame(game);
        //TODO: check on a way for ODM to send the updated object directly
        return gameMapper.toResource(gameRepository.findGameById(gameId));

    }

    public GameTO removePlayerFromGame(UserTO user, ObjectId gameId) {
        var game = gameRepository.findGameById(gameId);
        game.players.remove(user.id);
        game.eliminatedPlayers.add(user.id);
        gameRepository.saveGame(game);
        //TODO: check on a way for ODM to send the updated object directly
        return gameMapper.toResource(gameRepository.findGameById(gameId));
    }


    public GameTO continueToNextPlayer(ObjectId gameId,ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var currentRound = game.rounds.get(game.rounds.size() - 1);
        if (currentRound.currentActivePlayer == game.players.get(game.players.size() - 1)) {
            // if last player is putting card, shuffle cards and set currentplayer to 0
            currentRound.currentActivePlayer = game.players.get(0);
            currentRound.handMaidPlayer = null;
            // shuffle cards
            //TODO: dont do this, offloda as much as possible in userService Layer
            var players = game.players.stream().map(e -> userRepository.getUserById(e)).collect(Collectors.toList());
            for (UserDAO player : players) {
                var currentGame = player.currentGame;
                currentGame.cards.clear();
                var shuffledCard = CardUtils.giveCardToPlayer(game.cardsInShuffle);
                currentGame.cards.add(shuffledCard);
                game.cardsInShuffle.remove(shuffledCard);
                userRepository.save(player);
            }

        } else {
            var player = userRepository.getUserById(currentRound.currentActivePlayer);
            player.currentGame.cards.removeIf(e->e.id==cardId);
            //TODO: add card back in shuffle
            userRepository.save(player);
            currentRound.currentActivePlayer = game.players.get(game.players.indexOf(currentRound.currentActivePlayer) + 1);
        }

        gameRepository.saveGame(game);
        return gameMapper.toResource(game);


    }

    public GameTO eliminatePlayer(ObjectId gameId, ObjectId userId,ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        if (game.rounds.get(game.rounds.size() - 1).handMaidPlayer == userId)
            throw new HandMaidCanNotBeAttackedException();
        game.players.remove(userId);
        game.eliminatedPlayers.add(userId);
        if (game.players.size() == 1) {
            GameUtils.completeRound(game);
            gameRepository.saveGame(game);
            return gameMapper.toResource(game);
        }
        gameRepository.saveGame(game);
        return continueToNextPlayer(gameId,cardId);
    }

    public GameTO makePlayerImmune(ObjectId gameId, ObjectId userId,ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var currentRound = game.rounds.get(game.rounds.size() - 1);
        currentRound.handMaidPlayer = userId;
        gameRepository.saveGame(game);
        return continueToNextPlayer(gameId,cardId);
    }

    //TODO: could be on user service,  'joins' on nosql fucking me up
    public GameTO changeCard(ObjectId gameId, ObjectId userId,ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var player = userRepository.getUserById(userId);
        player.currentGame.cards.clear();
        var shuffledCard = CardUtils.giveCardToPlayer(game.cardsInShuffle);
        player.currentGame.cards.add(shuffledCard);
        game.cardsInShuffle.remove(shuffledCard);
        gameRepository.saveGame(game);
        userRepository.save(player);
        return continueToNextPlayer(gameId,cardId);
    }

    public GameTO tradeCards(ObjectId gameId, ObjectId userId, ObjectId attackedUserId,ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var initiator = userRepository.getUserById(userId);
        var attackedUser = userRepository.getUserById(attackedUserId);
        var tempInitiatorCard = initiator.currentGame.cards.get(0);
        initiator.currentGame.cards.set(0, attackedUser.currentGame.cards.get(0));
        attackedUser.currentGame.cards.set(0, tempInitiatorCard);
        userRepository.save(initiator);
        userRepository.save(attackedUser);
        return continueToNextPlayer(gameId,cardId);
    }

    public GameTO playPrincess(ObjectId gameId, ObjectId userId, ObjectId cardId){

        var game = gameRepository.findGameById(gameId);
        var initiator = userRepository.getUserById(userId);
        return continueToNextPlayer(gameId,cardId);
    }


}
