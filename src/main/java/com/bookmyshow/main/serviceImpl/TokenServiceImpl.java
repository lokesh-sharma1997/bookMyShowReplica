package com.bookmyshow.main.serviceImpl;

import java.time.Duration;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.security.JwtService;
import com.bookmyshow.main.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void saveToken(String token, Long userId ,long durationSeconds) {
		try {

			redisTemplate.opsForValue().set(userId.toString(), token,Duration.ofMillis(durationSeconds));
			System.out.println("Saved OID {} with name {} to Redis" + userId.toString());
		} catch (Exception e) {
			System.out.println("Failed to save token to Redis: " + e.getMessage());
			throw new RuntimeException("Failed to save OID", e);
		}
	}

	@Override
	public boolean isTokenValid(Long userId) {
		Set<String> keys = redisTemplate.keys("token:user:" + userId + ":*");
		return keys != null && !keys.isEmpty();
	}

	@Override
	public void deleteTokenFromRedis(Long key) {
		try {
			redisTemplate.delete(key.toString());
			System.out.println("Deleted key {} from Redis" + key);
		} catch (Exception e) {
			System.out.println("Failed to delete key {} from Redis: {}" + e.getMessage());
			throw new RuntimeException("Failed to delete key", e);
		}
	}

}
