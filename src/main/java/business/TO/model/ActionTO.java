package business.TO.model;

import org.bson.types.ObjectId;

import java.io.Serializable;

public abstract class ActionTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public ObjectId initiator;
    public CardTO card;
}
