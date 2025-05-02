package com.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

	@GetMapping("tasks")
	public ResponseEntity<String> getAssignUserTask(){
		return new ResponseEntity<>("welcome to blog",HttpStatus.OK);
	}
}
