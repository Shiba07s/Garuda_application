package com.garudaapplication.services.impl;

 
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
 

import com.garudaapplication.entities.Category;
import com.garudaapplication.entities.Post;
import com.garudaapplication.entities.User;
import com.garudaapplication.exceptions.ResourceNotFoundException;
import com.garudaapplication.payloads.PostDto;
import com.garudaapplication.payloads.PostResponse;
import com.garudaapplication.repositories.CategoryRepo;
import com.garudaapplication.repositories.PostRepo;
import com.garudaapplication.repositories.UserRepo;
import com.garudaapplication.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	 @Override
	    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		 
		 User user = this.userRepo.findById(userId)
		                                 .orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
		 Category category= this.categoryRepo.findById(categoryId)
				                         .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		 Post post = this.modelMapper.map(postDto, Post.class);
		 post.setImageName("default.png");
		 post.setAddedDate(new Date());
		 post.setUser(user);
		 post.setCategory(category);
		 
		 Post newPost = this.postRepo.save(post);
			return this.modelMapper.map(newPost,PostDto.class); 
 
	    }

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	 Post updatePost = this.postRepo.findById(postId).orElseThrow(() ->new ResourceNotFoundException( "post",  "post id", postId));
	 updatePost.setTitle(postDto.getTitle());
	 updatePost.setContent(postDto.getContent());
	 updatePost.setImageName(postDto.getImageName());
	 Post PostDtos = this.postRepo.save(updatePost);
	 return this.modelMapper.map(PostDtos, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
 
		 Post deletePost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException( "post",  "post id", postId));
		this.postRepo.delete(deletePost);
	}

	@Override
	public PostDto getSinglePost(Integer postId) {
		 Post posts = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException( "post",  "post id", postId));
		
		 return this.modelMapper.map(posts, PostDto.class);
	} 
	
	//pagination implements on get all posts 
	//we can create  pageable object where pagination methods are present
	//PAgeRequest.of() -> we can put the page size and pageNumber
	//getContent() -> we can convert page to list of the post
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) { 
	 //#1->if else statement to declare show dynamic sorting in api
		
//		Sort sort= null;
//		if (sortDir.equalsIgnoreCase( "asc")) {
//			sort=Sort.by(sortBy).ascending();
//		} else {
//            sort=Sort.by(sortBy).descending();
//		}
		
		//way-2: ternary operator we used to declare dynamic sorting in api
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		   Page<Post> pagePost = this.postRepo.findAll(pageable);
		   List<Post> allPosts = pagePost.getContent();
		   
		 List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				 .collect(Collectors.toList());
		 
		  PostResponse postResponse = new PostResponse();
		  postResponse.setContent(postDtos);
		  postResponse.setPageNumber(pagePost.getNumber());
		  postResponse.setPageSize(pagePost.getSize());
		  postResponse.setTotalElements(pagePost.getTotalElements());
 		  postResponse.setTotalPages(pagePost.getTotalPages());
 		 postResponse.setLastPage(pagePost.isLast());
		  
		  
		return postResponse;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		 Category cat = this.categoryRepo.findById(categoryId)
				            .orElseThrow(() -> new ResourceNotFoundException( "category",  "category id", categoryId));
		
		 List<Post> posts = this.postRepo.findByCategory(cat);
		 List<PostDto> postDtos = posts.stream().map( (post) -> this.modelMapper.map(post, PostDto.class))
				                                                                .collect(Collectors.toList());
		 return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		 User user = this.userRepo.findById(userId)
				 .orElseThrow(() -> new ResourceNotFoundException( "user", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		 List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		   return postDtos;
	}
	 @Override
	    public List<PostDto> searchPosts(String keywords) {
	        List<Post> posts = this.postRepo.searchByTitle("%" + keywords + "%");
	        List<PostDto> postDtoss = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	        return postDtoss;
	    }

	 

}
