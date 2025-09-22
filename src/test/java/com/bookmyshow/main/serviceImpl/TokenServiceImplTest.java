package com.bookmyshow.main.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.bookmyshow.main.exception.RedisOperationException;

class TokenServiceImplTest {

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private ValueOperations<String, Object> valueOperations;

	@InjectMocks
	private TokenServiceImpl tokenService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(redisTemplate.opsForValue()).thenReturn(valueOperations);
	}

	@Test
	void testSaveToken_Success() {
		assertDoesNotThrow(() -> tokenService.saveToken("test-token", 123L, 60000L));
		verify(valueOperations).set("123", "test-token", Duration.ofMillis(60000L));
	}

	@Test
	void testSaveToken_Exception() {
		doThrow(RuntimeException.class).when(valueOperations).set(anyString(), any(), any(Duration.class));

		assertThrows(RedisOperationException.class, () -> tokenService.saveToken("fail-token", 99L, 60000L));
	}

	@Test
	void testIsTokenValid_Exists() {
		when(redisTemplate.hasKey("123")).thenReturn(true);

		boolean result = tokenService.isTokenValid(123L);
		assertTrue(result);
	}

	@Test
	void testIsTokenValid_NotExists() {
		when(redisTemplate.hasKey("123")).thenReturn(false);

		boolean result = tokenService.isTokenValid(123L);
		assertFalse(result);
	}

	@Test
	void testIsTokenValid_Exception() {
		when(redisTemplate.hasKey(anyString())).thenThrow(RuntimeException.class);

		assertThrows(RedisOperationException.class, () -> tokenService.isTokenValid(123L));
	}

	@Test
	void testDeleteTokenFromRedis_Success() {
		assertDoesNotThrow(() -> tokenService.deleteTokenFromRedis(123L));
		verify(redisTemplate).delete("123");
	}

	@Test
	void testDeleteTokenFromRedis_Exception() {
		doThrow(RuntimeException.class).when(redisTemplate).delete("123");

		assertThrows(RedisOperationException.class, () -> tokenService.deleteTokenFromRedis(123L));
	}

	@Test
	void testRefreshTokenTTL_Success() {
		when(redisTemplate.hasKey("123")).thenReturn(true);
		when(redisTemplate.expire("123", Duration.ofMillis(300000L))).thenReturn(true);

		boolean result = tokenService.refreshTokenTTL(123L, 300000L);
		assertTrue(result);
	}

	@Test
	void testRefreshTokenTTL_KeyNotFound() {
		when(redisTemplate.hasKey("123")).thenReturn(false);

		boolean result = tokenService.refreshTokenTTL(123L, 300000L);
		assertFalse(result);
	}

	@Test
	void testRefreshTokenTTL_Exception() {
		when(redisTemplate.hasKey("123")).thenReturn(true);
		when(redisTemplate.expire(anyString(), any())).thenThrow(RuntimeException.class);

		assertThrows(RedisOperationException.class, () -> tokenService.refreshTokenTTL(123L, 300000L));
	}
}
