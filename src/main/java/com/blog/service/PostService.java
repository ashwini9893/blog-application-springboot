package com.blog.service;

import java.util.List;

import com.blog.dto.PostDto;
import com.blog.exception.ResourceNotFoundException;

public interface PostService {

	PostDto createPost(PostDto postDto, String username) throws Exception;

	PostDto updatePost(PostDto postDto, Long postId, Long userId) throws ResourceNotFoundException;

	void deletePost(Long postId) throws ResourceNotFoundException;

	PostDto getPostById(Long postId) throws ResourceNotFoundException;

	List<PostDto> getAllPosts();
}