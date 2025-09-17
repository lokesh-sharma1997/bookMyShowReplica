package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.EditProfileRequest;
import com.bookmyshow.main.response.UserProfileResponse;

public interface UserProfileService {

	UserProfileResponse updateProfile(Long userId, EditProfileRequest request);

	UserProfileResponse getProfile(Long userId);
}
