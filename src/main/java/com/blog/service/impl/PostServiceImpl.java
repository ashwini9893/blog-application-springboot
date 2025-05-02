package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dto.PostDto;
import com.blog.exception.ResourceNotFoundException;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, String username) {

		// Fetch user from username (email)
		User user = this.userRepository.findByEmail(username);

		// Map DTO to Entity
		Post post = this.modelMapper.map(postDto, Post.class);

		post.setAddedDate(new Date());
		post.setImageName(postDto.getImageName() != null ? postDto.getImageName() : "default.png");
		post.setUser(user);

		Post savedPost = this.postRepository.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto updatedPost, Long postId, Long userId) throws ResourceNotFoundException {

		Post existingPost = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found "));
		
		if(updatedPost.getTitle()!=null) {
			existingPost.setTitle(updatedPost.getTitle());
		}
       if(updatedPost.getContent()!=null) {
    	   existingPost.setContent(updatedPost.getContent());
       }
		
		if(updatedPost.getImageName()!=null) {
			existingPost.setImageName(updatedPost.getImageName());
		}
	

		Post updatedPostDto = this.postRepository.save(existingPost);
		return modelMapper.map(updatedPostDto, PostDto.class);
	}

	@Override
	public void deletePost(Long postId) throws ResourceNotFoundException {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with this postId" + postId));
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPostById(Long postId) throws ResourceNotFoundException {

		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found :" + postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = this.postRepository.findAll();
		return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}
