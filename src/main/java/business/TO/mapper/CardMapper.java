package business.TO.mapper;

import business.TO.model.CardTO;
import business.persistency.entity.CardDAO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "cdi")
public interface CardMapper {
    CardTO toResource(CardDAO card);
}