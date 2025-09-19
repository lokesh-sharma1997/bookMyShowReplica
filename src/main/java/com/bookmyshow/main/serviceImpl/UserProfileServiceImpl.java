package com.bookmyshow.main.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.EditProfileRequest;
import com.bookmyshow.main.exception.ResourceAlreadyExistsException;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.model.UserProfile;
import com.bookmyshow.main.repository.UserProfileRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.response.UserProfileResponse;
import com.bookmyshow.main.service.UserProfileService;

import java.lang.reflect.Field;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	private static final String DEFAULT_DATE = "01/01/1990";

	private boolean isDefaultValue(String value) {
		return value == null || value.isEmpty() || value.equalsIgnoreCase("string")
				|| value.equalsIgnoreCase("user@example.com") || value.equals("0000000000") || value.equals("000000")
				|| value.equals(DEFAULT_DATE);
	}

	private <T> void updateField(T entity, String fieldName, Object value) {
		try {
			if (value == null)
				return;

			String valueStr = value.toString();
			if (isDefaultValue(valueStr))
				return;

			Field field = entity.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);

			// Just set the value directly for all fields, including dates
			field.set(entity, valueStr);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException("Failed to update field: " + fieldName, e);
		}
	}

	@Override
	public UserProfileResponse updateProfile(Long userId, EditProfileRequest request) {
		UserMaster user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		UserProfile userProfile = user.getUserProfile();
		if (userProfile == null) {
			userProfile = new UserProfile();
			userProfile.setUser(user);
		}

		if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
			if (userRepository.existsByEmailAndDeleteFlag(request.getEmail(), false)) {
				throw new ResourceAlreadyExistsException("Email already exists: " + request.getEmail());
			}
		}

		if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
			if (userRepository.existsByUsername(request.getUsername())) {
				throw new ResourceAlreadyExistsException("Username already taken: " + request.getUsername());
			}
		}

		// Update fields
		updateField(user, "name", request.getName());
		updateField(user, "username", request.getUsername());
		updateField(user, "email", request.getEmail());
		updateField(user, "phoneNumber", request.getPhoneNumber());

		updateField(userProfile, "profileImg", request.getProfileImg());
		updateField(userProfile, "dob", request.getDob());
		updateField(userProfile, "identity", request.getIdentity());
		updateField(userProfile, "married", request.getMarried());
		updateField(userProfile, "anniversaryDate", request.getAnniversaryDate());
		updateField(userProfile, "pincode", request.getPincode());
		updateField(userProfile, "addressLine1", request.getAddressLine1());
		updateField(userProfile, "addressLine2", request.getAddressLine2());
		updateField(userProfile, "city", request.getCity());
		updateField(userProfile, "state", request.getState());

		userRepository.save(user);
		userProfileRepository.save(userProfile);

		return mapToResponse(user, userProfile);
	}

	@Override
	public UserProfileResponse getProfile(Long userId) {
		UserMaster user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		UserProfile userProfile = user.getUserProfile();
		return mapToResponse(user, userProfile);
	}

	private UserProfileResponse mapToResponse(UserMaster user, UserProfile profile) {
		UserProfileResponse response = new UserProfileResponse();
		response.setId(user.getUserId());
		response.setName(user.getName());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setPhoneNumber(user.getPhoneNumber());

		if (profile != null) {
			response.setProfileImg(profile.getProfileImg());
			response.setDob(profile.getDob());
			response.setIdentity(profile.getIdentity());
			response.setMarried(profile.getMarried());
			response.setAnniversaryDate(profile.getAnniversaryDate());
			response.setPincode(profile.getPincode());
			response.setAddressLine1(profile.getAddressLine1());
			response.setAddressLine2(profile.getAddressLine2());
			response.setCity(profile.getCity());
			response.setState(profile.getState());
		}
		return response;
	}
}
