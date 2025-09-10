package com.bookmyshow.main.serviceImpl;

import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.service.TokenService;

import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
	private final RedisTemplate<String, Object> redisTemplate;
	@Override
	public void saveToken(String token, Long userId, long durationSeconds) {
//		long duration = durationSeconds / 1000;
//		String redisKey = "token:user:" + userId + ":" + token;
		try {
		redisTemplate.opsForValue().setIfPresent(userId.toString(),  token);
		System.out.println("Saved token for user " + userId + ": " + userId.toString());
		}catch(RuntimeException e) {
			
			System.err.println(e.getMessage());
			
		}

		
	}

	@Override
	public boolean isTokenValid(Long userId) {
		 Set<String> keys = redisTemplate.keys("token:user:" + userId + ":*");
		    return keys != null && !keys.isEmpty();
	}

	@Override
	public void deleteToken(Long userId) {
		Set<String> keys = redisTemplate.keys("token:user:" + userId + ":*");
	    if (keys != null && !keys.isEmpty()) {
	        redisTemplate.delete(keys);
	        System.out.println("Deleted all tokens for user " + userId);
	    } else {
	        System.out.println("No tokens found for user " + userId);
	    }
		
	}

}
