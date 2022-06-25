package business.persistency.entity;

import org.bson.types.ObjectId;

import java.util.List;

public class CurrentGameStateDAO {
    public int points;
    public List<ActionDAO> actions;
    public List<CardDAO> cards;
}
