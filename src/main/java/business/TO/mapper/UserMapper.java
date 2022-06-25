package business.TO.mapper;

import business.TO.model.UserTO;
import business.persistency.entity.UserDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {
    UserTO toResource(UserDAO user);
}