package com.blog.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.config.JwtProvider;
import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public User getUserProfile(String jwt) {
		String email = JwtProvider.getEmailFromJwtToken(jwt);

		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

}
