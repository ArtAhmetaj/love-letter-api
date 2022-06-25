package business.service.impl;

import business.TO.mapper.CardMapper;
import business.TO.mapper.UserMapper;
import business.TO.model.CardTO;
import business.TO.model.UserTO;
import business.persistency.cache.CachedCardRepository;
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

    //TODO: check if graphql supports just id and not whole object here
    public int getCurrentWins(UserTO user){
        var currentUser = userRepository.getUserById(user.id);
        return currentUser.wins;
    }


}
