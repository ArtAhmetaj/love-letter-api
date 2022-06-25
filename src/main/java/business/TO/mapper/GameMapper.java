package business.TO.mapper;


import business.TO.model.GameTO;
import business.persistency.entity.GameDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface GameMapper {
    GameTO toResource(GameDAO game);
    GameDAO fromResource(GameTO game);
}