package business.persistency.repository.impl;

import business.persistency.entity.UserDAO;
import business.persistency.repository.IUserRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserDAO>, IUserRepository {
    @Override
    public UserDAO getUserById(ObjectId id) {
        return findById(id);
    }


}
