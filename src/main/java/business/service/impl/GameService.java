package business.service.impl;

import business.TO.mapper.CardMapper;
import business.TO.mapper.GameMapper;
import business.TO.model.GameTO;
import business.TO.model.UserTO;
import business.exceptions.models.business.HandMaidCanNotBeAttackedException;
import business.persistency.cache.CachedGameRepository;
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
    UserService userService;

    @Inject
    GameMapper gameMapper;

    @Inject
    CardMapper cardMapper;


    public GameTO getGameById(ObjectId id) {
        var gameDAO = gameRepository.findGameById(id);
        return gameMapper.toResource(gameDAO);
    }

    public GameTO createGame(GameTO game) {
        var gameEntity = gameMapper.fromResource(game);
        gameRepository.saveGame(gameEntity);
        return gameMapper.toResource(gameEntity);
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


    public GameTO continueToNextPlayer(ObjectId gameId, ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var currentRound = game.rounds.get(game.rounds.size() - 1);
        if (currentRound.currentActivePlayer == game.players.get(game.players.size() - 1)) {
            // if last player is putting card, shuffle cards and set currentplayer to 0
            currentRound.currentActivePlayer = game.players.get(0);
            currentRound.handMaidPlayer = null;
            var cards = CardUtils.getCardsForNewRound(game.cardsInShuffle, game.players.size());
            game.cardsInShuffle.removeAll(cards);
            //TODO: better to send map, either way order will be maintained
            userService.shuffleCards(game.players, cards.stream().map(e -> cardMapper.toResource(e)).collect(Collectors.toList()));

        } else {
            userService.removeUsedCard(currentRound.currentActivePlayer, cardId);
            currentRound.currentActivePlayer = game.players.get(game.players.indexOf(currentRound.currentActivePlayer) + 1);
        }

        var card = CardUtils.giveCardToPlayer(game.cardsInShuffle);
        game.cardsInShuffle.remove(card);
        userService.changeCard(currentRound.currentActivePlayer, cardMapper.toResource(card));

        gameRepository.saveGame(game);
        return gameMapper.toResource(game);
    }

    public GameTO eliminatePlayer(ObjectId gameId, ObjectId userId, ObjectId cardId) {
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
        return continueToNextPlayer(gameId, cardId);
    }

    public GameTO makePlayerImmune(ObjectId gameId, ObjectId userId, ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var currentRound = game.rounds.get(game.rounds.size() - 1);
        currentRound.handMaidPlayer = userId;
        gameRepository.saveGame(game);
        return continueToNextPlayer(gameId, cardId);
    }

    public GameTO changeCard(ObjectId gameId, ObjectId userId, ObjectId cardId) {
        var game = gameRepository.findGameById(gameId);
        var newCard = CardUtils.giveCardToPlayer(game.cardsInShuffle);
        game.cardsInShuffle.remove(newCard);
        userService.changeCard(userId, cardMapper.toResource(newCard));
        gameRepository.saveGame(game);
        return continueToNextPlayer(gameId, cardId);
    }

    public GameTO tradeCards(ObjectId gameId, ObjectId userId, ObjectId attackedUserId, ObjectId cardId) {
        // currently feels like a plain userService method, changes could be necessary so leaving it here for the moment
        userService.tradeCards(userId, attackedUserId, cardId);
        return continueToNextPlayer(gameId, cardId);
    }

    public GameTO playPrincess(ObjectId gameId, ObjectId userId, ObjectId cardId) {

        var game = gameRepository.findGameById(gameId);
        return continueToNextPlayer(gameId, cardId);
    }


}
