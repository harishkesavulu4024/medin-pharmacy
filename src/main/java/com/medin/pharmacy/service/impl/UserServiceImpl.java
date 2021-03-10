package com.medin.pharmacy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.UserDTO;
import com.medin.pharmacy.entities.User;
import com.medin.pharmacy.repository.UserRepository;
import com.medin.pharmacy.service.IUserService;
import com.medin.pharmacy.service.impl.mapper.UserMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> getAllUserDetails() {
		List<User> userList = userRepository.findAll();
		List<UserDTO> userDTOList = userList.stream().map(user -> userMapper.userToUserDTO(user))
				.collect(Collectors.toList());
		return userDTOList;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		String userName = userDTO.getUserName();
		// check userName already exist
		User existingUser = userRepository.findByUserName(userName);
		if (existingUser != null) {
			throw new BusinessException("User already exits,please try again");
		}
		User newUser = userRepository.save(userMapper.userDTOToUser(userDTO));
		UserDTO newUserDTO = userMapper.userToUserDTO(newUser);
		return newUserDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
	}

}
