package org.abhay.bookTicket.service;


import java.util.List;

import org.abhay.bookTicket.Entity.User;
import org.abhay.bookTicket.dto.UserDto;
import org.abhay.bookTicket.exception.ResourceNotFoundException;
import org.abhay.bookTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public UserDto createUser(UserDto userDto) {
		User user = mapToEntity(userDto);
		User savedUser= userRepo.save(user);
		return mapToUserDto(savedUser);
	}
	
	// findByEmail
	public UserDto findUserByEmail(String email) {
		User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User Not with email:"+email));
		return mapToUserDto(user);

	}
	
	//find by mobile
	public UserDto findUserByMobile(String number) {
		User user = userRepo.findByPhoneNumber(number).orElseThrow(()-> new ResourceNotFoundException("User Not with number:"+number));
		return mapToUserDto(user);
	}
	
	// find by user id
	public UserDto findByUserId(Long id) {
		User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User Not Found with id:" + id));
		return mapToUserDto(user);
	}
	//find all user
	
	public List<UserDto> findAllUser(){
		List<User> usersList = userRepo.findAll();
		return usersList.stream().map(this::mapToUserDto).toList();
	}
	// update user 
	
	public UserDto updateUser(Long id, UserDto userDto) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not with id:"+id));
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());
		userRepo.save(user);
		return mapToUserDto(user);
	}
		
	//delete user
	public void deleteUser(Long id) {
		User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not with id:"+id));
		userRepo.delete(user);
	}

	private UserDto mapToUserDto(User user) {
		// TODO Auto-generated method stub
		UserDto userDto= new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
		userDto.setPhoneNumber(user.getPhoneNumber());
		
		return userDto;
	}

	private User mapToEntity(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());
//		userRepo.save(user);
		
		return user;
	}
}

