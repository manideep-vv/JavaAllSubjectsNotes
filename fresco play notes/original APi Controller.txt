package com.fresco.dbrestapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.dbrestapi.model.Post;
import com.fresco.dbrestapi.model.Userposts;
import com.fresco.dbrestapi.repo.UserpostsRepository;

@RestController
public class ApiController {
	@Autowired	
	UserpostsRepository postsRepo;
	@CrossOrigin
	@PostMapping("/addpost")
	public String post(String postBody, String user) {
		return "OK 200";
	}

	@CrossOrigin
	@RequestMapping("/getposts")
	public Object[] getPosts(String user) {
		return new Object[0];
	}
	
	@CrossOrigin
	@RequestMapping("/delpost")
	public String delPosts(String user, String postId) {
		return "OK 200";
	}
	@CrossOrigin
	@RequestMapping("/searchuser")
	public HashMap<String, Boolean> searchUser(String user, String searchText) {
		return new HashMap<String, Boolean>();
	}
	
	@CrossOrigin
	@RequestMapping("/subscriber")
	public String subscriber(String user, String subuser) {
		return "OK 200";
	}
}
