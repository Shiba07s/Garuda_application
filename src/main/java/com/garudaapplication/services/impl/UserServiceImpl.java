package com.garudaapplication.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garudaapplication.entities.User;
import com.garudaapplication.exceptions.ResourceNotFoundException;
 import com.garudaapplication.payloads.UserDto;
import com.garudaapplication.repositories.UserRepo;
import com.garudaapplication.services.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		 User saved = this.userRepo.save(user);
		return this.userToDto(saved);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	 User user = this.userRepo.findById(userId)
			 .orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
	 user.setName(userDto.getName());
	 user.setEmail(userDto.getEmail());
	 user.setPassword(userDto.getPassword());
	 user.setAbout(userDto.getAbout());
	User updatedUser = this.userRepo.save(user);	
	 return this.userToDto(updatedUser);
	}

 

	@Override
	public UserDto getUserById(Integer userId) {
		User user1 = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user", "id",userId));
		 
		return this.userToDto(user1);
	}

	@Override
	public List<UserDto> getAllUsers() {
	 List<User> list = this.userRepo.findAll();
	 List<UserDto> collect = list.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user2 = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user", "id",userId));
		 this.userRepo.delete(user2);
		
	}
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
		
	}
	public UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail()); 
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}





	 

}
