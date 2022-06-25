package business.infra.graphql;


import business.TO.model.GameTO;
import business.service.impl.GameService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;

@GraphQLApi
public class GameResource {

    @Inject
    GameService gameService;

    @Mutation
    public GameTO createGame(GameTO game) {
        //TODO: sending gameTO is not a good idea
        return gameService.createGame(game);
    }


    @Query
    @Description("Get Game by id")
    public GameTO getGameById(@Name("gameId") ObjectId id) {
        return gameService.getGameById(id);
    }


}

