package business.persistency.repository;

import business.persistency.entity.CardDAO;

import java.util.List;

public interface ICardRepository {

    List<CardDAO> findAllCards();


}
