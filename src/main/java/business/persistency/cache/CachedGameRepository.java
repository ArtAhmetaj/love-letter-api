package business.persistency.cache;

import business.persistency.entity.GameDAO;
import business.persistency.repository.IGameRepository;
import business.persistency.repository.impl.GameRepository;
import business.provider.impl.CacheProvider;
import io.vertx.core.Vertx;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CachedGameRepository implements IGameRepository {

    @Inject
    CacheProvider cacheProvider;

    @Inject
    GameRepository gameRepository;

    @Inject
    Vertx vertx;

    @Override
    public GameDAO findGameById(ObjectId id) {
        var cachedGame = cacheProvider.get(CacheConstants.GAME + id.toString(), GameDAO.class);
        if (cachedGame != null) return cachedGame;

        return gameRepository.findGameById(id);
    }

    @Override
    public void saveGame(GameDAO game) {
        gameRepository.saveGame(game);
        //TODO: experimental, should check on thread safety
        vertx.executeBlocking(promise -> {
            cacheProvider.set(CacheConstants.GAME + game.id.toString(), game, GameDAO.class);
        });


    }
}
