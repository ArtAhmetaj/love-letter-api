package business.provider;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collection;
import java.util.List;

public interface ICacheProvider {
    <T> T get(String key,Class<T> clazz) throws JsonProcessingException;

    <T> T set(String key, T value,Class<T> clazz) throws JsonProcessingException;

    <T> List<T> setCollection(String key, List<T> collection, Class<T> clazz) throws JsonProcessingException;

    <T> List<T> getCollection(String key, Class<T> clazz) throws JsonProcessingException;

}
