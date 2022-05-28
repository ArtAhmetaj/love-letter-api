package business.persistency.repository;

import business.persistency.entity.UserDAO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserDAO> {
}
