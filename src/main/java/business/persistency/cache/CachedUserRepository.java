package business.persistency.cache;

import business.persistency.entity.UserDAO;
import business.persistency.repository.IUserRepository;
import business.persistency.repository.impl.UserRepository;
import business.provider.impl.CacheProvider;
import org.bson.types.ObjectId;

import javax.inject.Inject;

public class CachedUserRepository implements IUserRepository {
    @Inject
    CacheProvider cacheProvider;

    @Inject
    UserRepository userRepository;

    @Override
    public UserDAO getUserById(ObjectId id) {
        var user = cacheProvider.get(CacheConstants.USER+id.toString(),UserDAO.class);
        if(user !=null) return user;
        return userRepository.getUserById(id);
    }
}
