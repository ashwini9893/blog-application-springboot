package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.dto.PostDto;
import com.blog.exception.ResourceNotFoundException;
import com.blog.model.User;
import com.blog.service.PostService;
import com.blog.service.UserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	// CREATE POST
	@PostMapping
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User userProfile = userService.getUserProfile(jwt);
		PostDto createdPost = postService.createPost(postDto, userProfile.getEmail());
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	// UPDATE POST
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId,
			@RequestHeader("Authorization") String jwt) throws ResourceNotFoundException {
		User user = userService.getUserProfile(jwt);
		
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId, user.getId());
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	// DELETE POST
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId) throws ResourceNotFoundException {
		this.postService.deletePost(postId);
		return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
	}

	// GET POST BY ID
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) throws ResourceNotFoundException {
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	// GET ALL POSTS
	@GetMapping
	public ResponseEntity<List<PostDto>> getAllPosts() {
		List<PostDto> posts = this.postService.getAllPosts();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
}
