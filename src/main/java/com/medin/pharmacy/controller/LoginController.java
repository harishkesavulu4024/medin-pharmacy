package com.medin.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.JWTResponseSTO;
import com.medin.pharmacy.dto.LoginCredentialsDTO;
import com.medin.pharmacy.entities.User;
import com.medin.pharmacy.repository.UserRepository;
import com.medin.pharmacy.utils.BusinessException;
import com.medin.pharmacy.utils.JwtUtil;

@RestController
@RequestMapping(value="/login")
public class LoginController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private JwtUtil jwtUtil;
	
	@RequestMapping(method=RequestMethod.POST)
	public JWTResponseSTO login(@RequestBody LoginCredentialsDTO loginCredentials){
		String userName=loginCredentials.getUserName();
		String password=loginCredentials.getPassword();
		String token=null;
		User user=userRepository.findByUserName(userName);
		if(user!=null && user.getPassword().equals(password)){
			 token=jwtUtil.generateToken(userName);
		}else{
			throw new BusinessException("Invalid credentials");
		}
		return new JWTResponseSTO(token);
	}

}
