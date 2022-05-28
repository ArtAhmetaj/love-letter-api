package business.provider;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ICacheProvider {
    <T> T get(String key,Class<T> clazz) throws JsonProcessingException;

    <T> T set(String key, T value,Class<T> clazz) throws JsonProcessingException;
}
