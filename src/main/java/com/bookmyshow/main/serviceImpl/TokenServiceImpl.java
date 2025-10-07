package com.bookmyshow.main.serviceImpl;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.exception.RedisOperationException;
import com.bookmyshow.main.service.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

	private final RedisTemplate<String, Object> redisTemplate;

	// Saves a token in Redis with a TTL
	@Override
	public void saveToken(String token, Long userId, long durationSeconds) {
		try {
			redisTemplate.opsForValue().set(userId.toString(), token, Duration.ofMillis(durationSeconds));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RedisOperationException("Failed to save token for userId: " + userId, e);
		}
	}

	// Checks if token exists and is valid for the given userId
	@Override
	public boolean isTokenValid(Long userId) {
		try {
			String redisKey = userId.toString();
			Boolean exists = redisTemplate.hasKey(redisKey);
			return Boolean.TRUE.equals(exists);
		} catch (Exception e) {
			throw new RedisOperationException("Failed to validate token for userId: " + userId, e);
		}
	}

	// Deletes the token associated with the given userId from Redis
	@Override
	public void deleteTokenFromRedis(Long userId) {
		try {
			redisTemplate.delete(userId.toString());
		} catch (Exception e) {
			throw new RedisOperationException("Failed to delete token for userId: " + userId, e);
		}
	}

	// Refreshes the TTL of an existing token for the user, extending expiration
	// time
	@Override
	public boolean refreshTokenTTL(Long userId, long newDurationMillis) {
		try {
			String redisKey = userId.toString();
			Boolean exists = redisTemplate.hasKey(redisKey);

			if (Boolean.TRUE.equals(exists)) {
				Boolean result = redisTemplate.expire(redisKey, Duration.ofMillis(newDurationMillis));
				return Boolean.TRUE.equals(result);
			}

			return false;
		} catch (Exception e) {
			throw new RedisOperationException("Failed to extend TTL for userId: " + userId, e);
		}
	}
}
