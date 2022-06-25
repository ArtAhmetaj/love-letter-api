package business.TO.model;

import business.persistency.entity.RoundDAO;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class GameTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public ObjectId id;
    public LocalDateTime createdAt;
    public int numberOfPlayers;
    public LocalDateTime completedAt;
    public ObjectId creator;
    public ObjectId winner;
    public List<RoundDAO> rounds;
    public List<ObjectId> players;

}
