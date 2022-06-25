package business.persistency.repository;

import business.persistency.entity.UserDAO;
import org.bson.types.ObjectId;

import java.util.List;

public interface IUserRepository {
    UserDAO getUserById(ObjectId id);
    void save(UserDAO user);
}
