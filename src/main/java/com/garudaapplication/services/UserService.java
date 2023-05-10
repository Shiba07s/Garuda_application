package com.garudaapplication.services;

import java.util.List
;

import com.garudaapplication.entities.User;
import com.garudaapplication.payloads.UserDto;

 

 
public interface UserService {
	
	//create user
	UserDto createUser(UserDto userDto);
	
	//update user
	UserDto updateUser(UserDto userDto,Integer userId);
	
	//get single user
	UserDto getUserById(Integer userId);
	
	//get all records of the user
	List<UserDto> getAllUsers();
	 
	//delete user
	void deleteUser(Integer userId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
