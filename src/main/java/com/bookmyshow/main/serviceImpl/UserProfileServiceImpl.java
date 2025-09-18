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

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	// Utility method to check if the value is a "default" value
	private boolean isDefaultValue(String value) {
		return value != null && (value.equals("string") || value.equals("user@example.com") || value.equals("")
				|| value.equals("0000000000") // Add more default values as needed
		);
	}

	@Override
	public UserProfileResponse updateProfile(Long userId, EditProfileRequest request) {
		// Fetch the existing user from the database
		UserMaster user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// Fetch or create user profile
		UserProfile userProfile = user.getUserProfile();
		if (userProfile == null) {
			userProfile = new UserProfile();
			userProfile.setUser(user);
		}

		// Check if the email or username already exists
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new ResourceAlreadyExistsException("Email already exists: " + request.getEmail());
		}
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new ResourceAlreadyExistsException("Username already taken: " + request.getUsername());
		}

		// Update UserMaster fields (only if non-null, non-empty, and not a default
		// value)
		if (request.getName() != null && !isDefaultValue(request.getName())) {
			user.setName(request.getName());
		}
		if (request.getUsername() != null && !isDefaultValue(request.getUsername())) {
			user.setUsername(request.getUsername());
		}
		if (request.getEmail() != null && !isDefaultValue(request.getEmail())) {
			user.setEmail(request.getEmail());
		}
		if (request.getPhoneNumber() != null && !isDefaultValue(request.getPhoneNumber())) {
			user.setPhoneNumber(request.getPhoneNumber());
		}

		// Update UserProfile fields (only if non-null, non-empty, and not a default
		// value)
		if (request.getProfileImg() != null && !isDefaultValue(request.getProfileImg())) {
			userProfile.setProfileImg(request.getProfileImg());
		}
		if (request.getDob() != null && !isDefaultValue(request.getDob())) {
			userProfile.setDob(request.getDob());
		}
		if (request.getIdentity() != null && !isDefaultValue(request.getIdentity())) {
			userProfile.setIdentity(request.getIdentity());
		}
		if (request.getMarried() != null && !isDefaultValue(request.getMarried())) {
			userProfile.setMarried(request.getMarried());
		}
		if (request.getAnniversaryDate() != null && !isDefaultValue(request.getAnniversaryDate())) {
			userProfile.setAnniversaryDate(request.getAnniversaryDate());
		}
		if (request.getPincode() != null && !isDefaultValue(request.getPincode())) {
			userProfile.setPincode(request.getPincode());
		}
		if (request.getAddressLine1() != null && !isDefaultValue(request.getAddressLine1())) {
			userProfile.setAddressLine1(request.getAddressLine1());
		}
		if (request.getAddressLine2() != null && !isDefaultValue(request.getAddressLine2())) {
			userProfile.setAddressLine2(request.getAddressLine2());
		}
		if (request.getCity() != null && !isDefaultValue(request.getCity())) {
			userProfile.setCity(request.getCity());
		}
		if (request.getState() != null && !isDefaultValue(request.getState())) {
			userProfile.setState(request.getState());
		}

		// Save both entities
		userRepository.save(user);
		userProfileRepository.save(userProfile);

		// Map the updated entities to the response object
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
