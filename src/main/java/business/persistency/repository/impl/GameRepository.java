package business.persistency.repository.impl;

import business.persistency.entity.GameDAO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GameRepository implements PanacheMongoRepository<GameDAO> {

}