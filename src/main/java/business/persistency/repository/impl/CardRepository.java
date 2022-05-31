package business.persistency.repository.impl;

import business.persistency.entity.CardDAO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CardRepository implements PanacheMongoRepository<CardDAO> {



}
