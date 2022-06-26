package business.TO.model;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class UserTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public ObjectId id;
    public String nickname;
    public int wins;
    public String accessToken;

}
