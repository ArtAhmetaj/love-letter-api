package business.infra.graphql;

import business.TO.model.UserTO;
import business.service.impl.UserService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;

@GraphQLApi
public class UserResource {

    @Inject
    UserService userService;

    @Query
    @Description("Get User by Id")
    public UserTO getGameById(@Name("userId") ObjectId id) {
        return userService.getUserById(id);
    }

    @Description("Get wins of current user")
    public int getWinsOfUser(@Source UserTO user) {
        return userService.getCurrentWins(user);
    }
}
