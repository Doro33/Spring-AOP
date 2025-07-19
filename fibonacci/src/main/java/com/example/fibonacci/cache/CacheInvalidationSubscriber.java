package com.example.fibonacci.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class CacheInvalidationSubscriber {

    private final CacheManager cacheManager;

    @Autowired
    public CacheInvalidationSubscriber(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter((MessageListener) (message, pattern) -> {
            String key = new String(message.getBody(), StandardCharsets.UTF_8);
            Objects.requireNonNull(cacheManager.getCache("fibonacci")).evict(key);
            System.out.println("⛔ Cache evicted for key: " + key);
        });
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            MessageListenerAdapter messageListenerAdapter
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, new ChannelTopic("cache-invalidation"));
        return container;
    }
}

