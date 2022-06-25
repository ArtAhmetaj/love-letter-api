package business.TO.model;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class CardTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    public ObjectId cardId;
    public CardNameTO cardName;
    public int strength;
    public int numberInDeck;
}
