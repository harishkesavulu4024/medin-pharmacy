package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.UserDTO;
import com.medin.pharmacy.entities.User;
import com.medin.pharmacy.service.IUserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public UserDTO addUser(@RequestBody UserDTO userDTO){
		return userService.createUser(userDTO);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public List<UserDTO> getUserDetails(){
		return userService.getAllUserDetails();
	}
	

}
