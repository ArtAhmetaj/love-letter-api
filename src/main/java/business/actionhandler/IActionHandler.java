package business.actionhandler;

import business.TO.model.*;
import org.bson.types.ObjectId;

public interface IActionHandler {
    public GameTO connectToGame(ObjectId userId,ObjectId gameId);
    public GameTO disconnectFromGame(ObjectId userId, ObjectId gameId);
    public GameTO attackPlayerWithGuard(ObjectId gameId, GuardAttackActionTO guardAttackAction);
    public GameTO savePlayerWithHandMaid(ObjectId gameId, HandmaidAttackActionTO handmaidAttackAction);
    public CardTO getCardOfPlayerWithPriest(ObjectId gameId, PriestAttackActionTO priestAttackAction);
    public GameTO compareHandsWithBaron(ObjectId gameId, BaronAttackActionTO baronAttackAction);
    public GameTO makePlayerDiscardWithPrince(ObjectId gameId, PrinceAttackActionTO princeAttackAction);
    public GameTO tradeHandsWithKing(ObjectId gameId,KingAttackActionTO kingAttackAction);
    public GameTO discardCardWithCountess(ObjectId gameId,CountessAttackActionTO countessAttackAction);
    public GameTO playRoundWithPrincess(ObjectId gameID, PrincessAttackActionTO princessAttackAction);


}
