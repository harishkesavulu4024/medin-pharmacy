package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.UserDTO;
import com.medin.pharmacy.entities.User;



@Mapper(componentModel = "spring", uses = {})
public interface UserMapper  {
		
	User userDTOToUser(UserDTO userDTO);
	
	UserDTO userToUserDTO(User user);
	
}