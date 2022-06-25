package business.TO.model;

import org.bson.types.ObjectId;

public class GuardAttackActionTO extends ActionTO{
    public ObjectId attackedPlayer;
    public ObjectId cardGuess;
}
