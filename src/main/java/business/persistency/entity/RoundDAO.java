package business.persistency.entity;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class RoundDAO  {
    public LocalDateTime createdDate;
    public ObjectId currentActivePlayer;
    public ObjectId winner;
    //doesnt make sense since its not removed at end of round, should do for now
    public ObjectId handMaidPlayer;
    public LocalDateTime completedDate;

}
