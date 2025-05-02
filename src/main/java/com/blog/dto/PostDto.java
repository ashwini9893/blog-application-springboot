package com.blog.dto;

import java.util.Date;

import com.blog.model.User;

import lombok.Data;

@Data
public class PostDto {

	private Long id;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private User user;
	
	   
	
}
