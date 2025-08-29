package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;

public interface AuthService {
	String login(LoginRequest req);
	String register(RegisterRequest req);
}
