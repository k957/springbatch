package com.rest.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rest.dto.UserDto;
import com.rest.model.User;

@Component
public class UserAssembler {
	
	public List<User> createUserEntity(List<UserDto> userDtoList){
		List<User> userList = new ArrayList<>();
		userDtoList.forEach(userDto -> {
			User user =new User();
			user.setName(userDto.getName());
			user.setEmailId(userDto.getEmailId());
			user.setGender(userDto.getGender());
			
			userList.add(user);
		});
		return userList;
	}
	
	public User createUserEntity(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmailId(userDto.getEmailId());
		user.setGender(userDto.getGender());
		return user;
	}
}
