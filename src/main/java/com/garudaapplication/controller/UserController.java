package com.garudaapplication.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garudaapplication.payloads.ApiResponse;
import com.garudaapplication.payloads.UserDto;
import com.garudaapplication.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//CREATE
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}
	//UPDATE
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto>updateUser(@RequestBody UserDto userDto,@PathVariable Integer userId){
		UserDto updateUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updateUser);
	}
	//RETRIEVE: get single user id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto>getUserById(@PathVariable Integer userId){
		UserDto getSingleUser = this.userService.getUserById(userId);
		return ResponseEntity.ok(getSingleUser);
		
	}
	//RETRIEVE:get all users data
		@GetMapping("/")
		public ResponseEntity<List<UserDto>>getAllUsers(){
			 List<UserDto> allUsers = this.userService.getAllUsers();
			return ResponseEntity.ok(allUsers);
	 		
		}
		       
	//DELETE
		@DeleteMapping("/{userId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
			this.userService.deleteUser(userId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true,HttpStatus.NOT_FOUND), HttpStatus.OK);
		}

}
