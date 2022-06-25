package business.provider.impl;

import business.provider.ICacheProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.quarkus.redis.client.RedisClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class CacheProvider implements ICacheProvider {
    @Inject
    RedisClient redisClient;


    @Inject
    ObjectMapper objectMapper;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        var response = redisClient.get(key);
        if (response == null) return null;
        try {
            return objectMapper.readValue(response.toString(), clazz);
        } catch (JsonProcessingException e) {
            Log.warn("Cache value of card did not get serialized correctly");
            return null;
        }

    }

    @Override
    public <T> T set(String key, T value, Class<T> clazz) {
        try {
            var response = redisClient.set(Arrays.asList(key, objectMapper.writeValueAsString(value)));
            return objectMapper.readValue(response.toString(), clazz);
        } catch (JsonProcessingException e) {
            Log.warn("Cache value of card did not get serialized correctly");
            return null;
        }

    }

    @Override
    public <T> List<T> setCollection(String key, List<T> collection, Class<T> clazz) {
        try {
            var response = redisClient.set(Arrays.asList(key, objectMapper.writeValueAsString(collection)));
            return objectMapper.readValue(response.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            Log.warn("Cache value of card did not get serialized correctly");
            return null;
        }
    }

    @Override
    public <T> List<T> getCollection(String key, Class<T> clazz) {
        try {
            var response = redisClient.get(key);
            return objectMapper.readValue(response.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
