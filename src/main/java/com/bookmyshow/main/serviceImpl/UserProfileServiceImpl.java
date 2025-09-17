package com.bookmyshow.main.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.EditProfileRequest;

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

	@Override
	public UserProfileResponse updateProfile(Long userId, EditProfileRequest request) {
		UserMaster user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		UserProfile userProfile = user.getUserProfile();
		if (userProfile == null) {
			userProfile = new UserProfile();
			userProfile.setUser(user);
		}

		// Update editable fields from UserMaster
		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());

		// Update extra fields from EditProfile
		userProfile.setProfileImg(request.getProfileImg());
		userProfile.setDob(request.getDob());
		userProfile.setIdentity(request.getIdentity());
		userProfile.setMarried(request.getMarried());
		userProfile.setAnniversaryDate(request.getAnniversaryDate());
		userProfile.setPincode(request.getPincode());
		userProfile.setAddressLine1(request.getAddressLine1());
		userProfile.setAddressLine2(request.getAddressLine2());
		userProfile.setCity(request.getCity());
		userProfile.setState(request.getState());
		userProfile.setCountry(request.getCountry());

		// Save both entities
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
			response.setCountry(profile.getCountry());
		}

		return response;
	}
}
