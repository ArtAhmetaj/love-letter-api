package business.persistency.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@MongoEntity(collection="Card")
public class CardDAO {
    public ObjectId id;
    public CardNameDAO cardName;
    public int strength;
    public int numberInDeck;
    public LocalDate createdAt;
}
