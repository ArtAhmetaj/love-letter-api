package business.persistency.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@MongoEntity(collection="Game")
public class GameDAO {
    public ObjectId id;
    public LocalDateTime createdAt;
    public int numberOfPlayers;
    public LocalDateTime completedAt;
    public ObjectId creator;
    public ObjectId winner;
    public List<RoundDAO> rounds;
    public List<ObjectId> players;

}
