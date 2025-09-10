package com.bookmyshow.main.service;

public interface TokenService {
	
	void saveToken(String token, Long userId, long durationSeconds);

	boolean isTokenValid(Long userId);

	void deleteToken(Long userId);
}