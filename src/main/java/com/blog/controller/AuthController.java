package com.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.config.JwtProvider;
import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.request.LoginRequest;
import com.blog.response.AuthResponse;
import com.blog.service.impl.CustomerUserServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomerUserServiceImpl customerUserDetails;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
		
		
		String fullName = user.getFullName();
		String email = user.getEmail();
		String password = user.getPassword();
		String role = user.getRole();

		User isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist != null) {
			throw new Exception("Email is Already used with another account");
		}
		
		// create new user
		User createUser = new User();
		createUser.setFullName(fullName);
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setRole(role);
		User savedUser = userRepository.save(createUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = JwtProvider.generateToken(authentication);

		AuthResponse authResponse = new AuthResponse();
		authResponse.setMessage("Register Success");
		authResponse.setJwt(token);
		authResponse.setStatus(true);

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
		
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		System.out.println("email :"+email+"   "+"password :"+password);
		
		Authentication authentication = authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String generatetoken = JwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setMessage("Login success ");
		authResponse.setJwt(generatetoken);
		authResponse.setStatus(true);
		return new ResponseEntity<>(authResponse,HttpStatus.OK);
		
	}
	
	private Authentication authenticate (String email, String password) {
		 UserDetails userDetails = customerUserDetails.loadUserByUsername(email);
		 
		 System.out.println("sign in userDetails- "+userDetails);
		 
		 if(userDetails == null) {
			 System.out.println("sign in userDetais - null"+userDetails);
			 throw new BadCredentialsException("invalid username or password");
		 }
		 if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			 throw new BadCredentialsException("Invalid username or password");
		 }
		 return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
	
	
}