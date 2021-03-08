package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.UserDTO;

public interface IUserService {

	List<UserDTO> getAllUserDetails();

	UserDTO createUser(UserDTO userDTO);
	
	

}
