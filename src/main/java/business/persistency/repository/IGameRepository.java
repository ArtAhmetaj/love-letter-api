package business.persistency.repository;

import business.persistency.entity.GameDAO;
import org.bson.types.ObjectId;

public interface IGameRepository {

    GameDAO findGameById(ObjectId id);
    void saveGame(GameDAO game);
}
