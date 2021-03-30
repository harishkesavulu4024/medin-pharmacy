package com.medin.pharmacy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.repository.QueueRepository;
import com.medin.pharmacy.service.ICustomerService;
import com.medin.pharmacy.service.impl.mapper.CustomerMapper;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private QueueRepository queueRepository;

	@Override
	public CustomerDTO addCustomerDetails(CustomerDTO customerDTO) {
		String otp=RandomString.make(4);
		// TODO Auto-generated method stub
		queueRepository.sendOtp(otp);
		return null;
	}

}
