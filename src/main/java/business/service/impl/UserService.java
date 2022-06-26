package business.service.impl;

import business.TO.mapper.CardMapper;
import business.TO.mapper.UserMapper;
import business.TO.model.CardTO;
import business.TO.model.UserTO;
import business.exceptions.models.business.CardNotFoundException;
import business.exceptions.models.server.PlayersNotMatchingSizeException;
import business.persistency.cache.CachedUserRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    CachedUserRepository userRepository;


    @Inject
    UserMapper userMapper;

    @Inject
    CardMapper cardMapper;


    public UserTO getUserById(ObjectId userId) {
        var user = userRepository.getUserById(userId);
        return userMapper.toResource(user);

    }


    public List<CardTO> getCurrentCards(ObjectId userId) {
        var user = userRepository.getUserById(userId);
        return user.currentGame.cards.stream().map(cardMapper::toResource).collect(Collectors.toList());
    }

    public int getCurrentWins(UserTO user) {
        var currentUser = userRepository.getUserById(user.id);
        return currentUser.wins;
    }


    public void removeUsedCard(ObjectId userId, ObjectId cardId) {
        var user = userRepository.getUserById(userId);
        user.currentGame.cards.removeIf(e -> e.id == cardId);
        userRepository.save(user);

    }

    public void shuffleCards(List<ObjectId> players, List<CardTO> cards) {
        //TODO: add hashmap and add native query for updates
        if (players.size() != cards.size()) throw new PlayersNotMatchingSizeException();
        for(int i =0;i<players.size();i++){
            var player = userRepository.getUserById(players.get(i));
            player.currentGame.cards.clear();
            player.currentGame.cards.add(cardMapper.fromResource(cards.get(i)));
            userRepository.save(player);
        }
    }

    public void changeCard(ObjectId userId,CardTO newCard){
        var user = userRepository.getUserById(userId);
        user.currentGame.cards.clear();
        user.currentGame.cards.add(cardMapper.fromResource(newCard));
        userRepository.save(user);
    }

    public void tradeCards(ObjectId userId, ObjectId initiatedCard, ObjectId attackedUserId){
        var initiator = userRepository.getUserById(userId);
        var attackedUser = userRepository.getUserById(attackedUserId);

        var temp = initiator.currentGame.cards.stream().filter(e->e.id==initiatedCard).findFirst();
        if(temp.isEmpty()) throw new CardNotFoundException();
        initiator.currentGame.cards.set(0,attackedUser.currentGame.cards.get(0));
        attackedUser.currentGame.cards.set(0,temp.get());
        userRepository.save(initiator);
        userRepository.save(attackedUser);


    }


}
