package com.garudaapplication.services;

import java.util.List;

import com.garudaapplication.payloads.PostDto;
import com.garudaapplication.payloads.PostResponse;
 

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	//get single post
	PostDto getSinglePost(Integer postId);
	
	//get all posts by category
	
		List<PostDto> getPostsByCategory(Integer categoryId);
		
		//get all posts by user
		List<PostDto> getPostsByUser(Integer userId);
       
		//search posts
		List<PostDto> searchPosts(String keywords);
}
