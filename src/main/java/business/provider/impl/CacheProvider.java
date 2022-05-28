package business.provider.impl;

import business.provider.ICacheProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.redis.client.RedisClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;

@ApplicationScoped
public class CacheProvider implements ICacheProvider {
    @Inject
    RedisClient redisClient;


    @Inject
    ObjectMapper objectMapper;

    @Override
    public <T> T get(String key,Class<T> clazz) throws JsonProcessingException {
        var response = redisClient.get(key);
        if(response == null) return null;
        return objectMapper.readValue(response.toString(),clazz);

    }

    @Override
    public <T> T set(String key, T value,Class<T> clazz) throws JsonProcessingException {
       var response = redisClient.set(Arrays.asList(key,value.toString()));
       return objectMapper.readValue(response.toString(),clazz);

    }
}
