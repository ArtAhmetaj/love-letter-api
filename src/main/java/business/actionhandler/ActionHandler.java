package business.actionhandler;

import business.TO.model.*;
import business.exceptions.AttackedCardMustNotBeGuardException;
import business.exceptions.CardNotFoundException;
import business.service.impl.CardService;
import business.service.impl.GameService;
import business.service.impl.UserService;
import business.validator.CardValidator;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ActionHandler implements IActionHandler {

    @Inject
    GameService gameService;

    @Inject
    UserService userService;

    @Inject
    CardService cardService;

    @Override
    public GameTO connectToGame(ObjectId userId, ObjectId gameId) {
        var user = userService.getUserById(userId);
        return gameService.addPlayerToGame(user, gameId);

    }

    @Override
    public GameTO disconnectFromGame(ObjectId userId, ObjectId gameId) {
        var user = userService.getUserById(userId);
        return gameService.addPlayerToGame(user, gameId);
    }

    @Override
    public GameTO attackPlayerWithGuard(ObjectId gameId, GuardAttackActionTO guardAttackAction) {
        var initiatorCards = userService.getCurrentCards(guardAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.GUARD);
        var attackedUserCards = userService.getCurrentCards(guardAttackAction.attackedPlayer);

        var guessedCardOptional = cardService.getAllCards().stream().filter(e -> e.cardId == guardAttackAction.cardGuess).findFirst();
        if (guessedCardOptional.isEmpty()) throw new CardNotFoundException();
        var guessedCard = guessedCardOptional.get();
        if (guessedCard.cardName == CardNameTO.GUARD) throw new AttackedCardMustNotBeGuardException();

        if (attackedUserCards.stream().anyMatch(e -> e.cardId == guessedCard.cardId)) {
            // remove user from game
            // return game state
            return gameService.eliminatePlayer(gameId, guardAttackAction.attackedPlayer,guardAttackAction.card.cardId);
        } else {
            return gameService.continueToNextPlayer(gameId,guardAttackAction.card.cardId);
        }

    }

    @Override
    public GameTO savePlayerWithHandMaid(ObjectId gameId, HandmaidAttackActionTO handmaidAttackAction) {
        var initiatorCards = userService.getCurrentCards(handmaidAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.HANDMAID);
        return gameService.makePlayerImmune(gameId, handmaidAttackAction.initiator,handmaidAttackAction.card.cardId);
    }

    @Override
    public CardTO getCardOfPlayerWithPriest(ObjectId gameId, PriestAttackActionTO priestAttackAction) {
        var initiatorCards = userService.getCurrentCards(priestAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.PRIEST);
        var attackedPlayerCards = userService.getCurrentCards(priestAttackAction.attackedPlayer);
        // always under the assumption that during this point in time the user has one card
        gameService.continueToNextPlayer(gameId,priestAttackAction.card.cardId);
        return attackedPlayerCards.get(0);
    }

    @Override
    public GameTO compareHandsWithBaron(ObjectId gameId, BaronAttackActionTO baronAttackAction) {
        var initiatorCards = userService.getCurrentCards(baronAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.BARON);
        var attackedPlayerCards = userService.getCurrentCards(baronAttackAction.attackedPlayer);
        if (initiatorCards.get(0).strength > attackedPlayerCards.get(0).strength) {
            return gameService.eliminatePlayer(gameId, baronAttackAction.attackedPlayer,baronAttackAction.card.cardId);
        }
        return gameService.eliminatePlayer(gameId, baronAttackAction.initiator,baronAttackAction.card.cardId);
    }

    @Override
    public GameTO makePlayerDiscardWithPrince(ObjectId gameId, PrinceAttackActionTO princeAttackAction) {
        var initiatorCards = userService.getCurrentCards(princeAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.PRINCE);
        // here this is fucky
        return gameService.changeCard(gameId, princeAttackAction.discardCardPlayer,princeAttackAction.card.cardId);
    }

    @Override
    public GameTO tradeHandsWithKing(ObjectId gameId, KingAttackActionTO kingAttackAction) {
        var initiatorCards = userService.getCurrentCards(kingAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.KING);
        return gameService.tradeCards(gameId,kingAttackAction.initiator,kingAttackAction.tradeCardPlayer,kingAttackAction.card.cardId);
    }

    @Override
    public GameTO discardCardWithCountess(ObjectId gameId, CountessAttackActionTO countessAttackAction) {
        //TODO: add validator on all endpoints for countess
        var initiatorCards = userService.getCurrentCards(countessAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.COUNTESS);
        return gameService.continueToNextPlayer(gameId,countessAttackAction.card.cardId);
    }

    @Override
    public GameTO playRoundWithPrincess(ObjectId gameId,PrincessAttackActionTO princessAttackAction) {
        //TODO: add specific logic for princess
        var initiatorCards = userService.getCurrentCards(princessAttackAction.initiator);
        CardValidator.cardBelongsToUser(initiatorCards, CardNameTO.PRINCESS);
        return gameService.playPrincess(gameId,princessAttackAction.initiator,princessAttackAction.card.cardId);
    }


}
