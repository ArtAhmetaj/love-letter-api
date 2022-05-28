package business.persistency.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@MongoEntity(collection="User")
public class UserDAO  {
    public ObjectId id; // used by MongoDB for the _id field
    public String nickname;
    public LocalDate createdAt;
    public int wins;
    public CurrentGameStateDAO currentGame;

}