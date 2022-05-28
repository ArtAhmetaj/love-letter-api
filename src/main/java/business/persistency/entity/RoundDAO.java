package business.persistency.entity;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class RoundDAO  {
    public LocalDateTime createdDate;
    public ObjectId currentActivePlayer;
    public ObjectId winner;
    public LocalDateTime completedDate;

}
