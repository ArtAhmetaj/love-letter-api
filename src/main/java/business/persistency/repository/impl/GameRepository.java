package business.persistency.repository.impl;

import business.persistency.entity.GameDAO;
import business.persistency.repository.IGameRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameRepository implements PanacheMongoRepository<GameDAO>, IGameRepository {

    @Override
    public GameDAO findGameById(ObjectId id) {
        return findById(id);
    }

    @Override
    public void saveGame(GameDAO game) {

        persist(game);
    }
}