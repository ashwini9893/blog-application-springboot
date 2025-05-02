package com.blog.service;

import java.util.List;

import com.blog.model.User;

public interface UserService {

	public User getUserProfile(String jwt);
	public List<User> getAllUsers();
}
