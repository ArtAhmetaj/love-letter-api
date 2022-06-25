package business.persistency.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class ActionDAO {
    public LocalDateTime createdAt;
    public ObjectId initiator;
    public ObjectId initiatorCard;
    public ObjectId attackedPlayer;

}
