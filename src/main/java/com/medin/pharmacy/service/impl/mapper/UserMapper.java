package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.UserDTO;
import com.medin.pharmacy.entities.User;



@Mapper(componentModel = "spring", uses = {})
public interface UserMapper  {
	
	//UserMapper  INSTANCE=Mappers.getMapper(UserMapper.class);
	
	User userTouserDTO(UserDTO userDTO);
	
	UserDTO userDTOToUser(User user);
	
}