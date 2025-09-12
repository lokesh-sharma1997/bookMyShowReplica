package com.bookmyshow.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RedisConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        
        try {
            if (redisPassword != null && !redisPassword.isEmpty()) {
                config.setUsername(redisUsername);
                config.setPassword(redisPassword);
            }
            return new LettuceConnectionFactory(config);
        } catch (Exception e) {
            logger.error("Error configuring Redis connection: {}", e.getMessage());
            throw new RuntimeException("Failed to configure Redis connection", e);  
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        try {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory());
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new StringRedisSerializer());
            template.setHashKeySerializer(new StringRedisSerializer());
            template.setHashValueSerializer(new StringRedisSerializer());
            return template;
        } catch (Exception e) {
            logger.error("Error creating RedisTemplate: {}", e.getMessage());
            throw new RuntimeException("Failed to create RedisTemplate", e);  
        }
    }
}
