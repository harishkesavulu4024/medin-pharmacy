package com.medin.pharmacy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.dto.LoyaltyCardDTO;
import com.medin.pharmacy.entities.CustomerLoyalty;
import com.medin.pharmacy.repository.CustomerLoyaltyRepository;
import com.medin.pharmacy.service.ICustomerLoyaltyService;
import com.medin.pharmacy.service.ILoyaltyCardService;
import com.medin.pharmacy.service.impl.mapper.CustomerLoyaltyMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class CustomerLoyaltyServiceImpl implements ICustomerLoyaltyService {

	@Autowired
	private CustomerLoyaltyMapper customerLoyaltyMapper;
	
	@Autowired
	private ILoyaltyCardService loyaltyCardService;
	


	@Autowired
	private CustomerLoyaltyRepository customerLoyaltyRepository;
 
	@Override
	@Transactional
	public CustomerLoyaltyDTO createCustomerLoyalty(CustomerLoyalty customerLoyalty) {
		CustomerLoyalty dbCustomerLoyalty = customerLoyaltyRepository.save(customerLoyalty);
		CustomerLoyaltyDTO customerLoyaltyDTO=customerLoyaltyMapper.customerLoyaltyToCustomerLoyaltyDTO(dbCustomerLoyalty);
		return customerLoyaltyDTO;
	}
	
	@Override
	@Transactional
	public CustomerLoyaltyDTO createCustomerLoyalty(CustomerLoyaltyDTO customerLoyaltyDTO) {
		LoyaltyCardDTO loyaltyCardDTO=loyaltyCardService.getDefaultLoyaltyCard();
		customerLoyaltyDTO.setLoyaltyCard(loyaltyCardDTO);
		CustomerLoyalty customerLoyalty = 
				customerLoyaltyMapper.customerLoyaltyDTOToCustomerLoyalty(customerLoyaltyDTO);
		CustomerLoyalty dbCustomerLoyalty = customerLoyaltyRepository.save(customerLoyalty);
		CustomerLoyaltyDTO cdbCustomerLoyaltyDTO=customerLoyaltyMapper.customerLoyaltyToCustomerLoyaltyDTO(dbCustomerLoyalty);
		return cdbCustomerLoyaltyDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerLoyaltyDTO getCustomerLoyaltyDetailsByMobileNumber(String mobileNumber) {
		CustomerLoyalty customerLoyalty = customerLoyaltyRepository.findCustomerLoyaltyByMobileNumber(mobileNumber);
		if(customerLoyalty==null){
			throw new BusinessException("Customer Loyalty not found :"+mobileNumber);
		}
		CustomerLoyaltyDTO customerLoyaltyDTO=customerLoyaltyMapper.customerLoyaltyToCustomerLoyaltyDTO(customerLoyalty);
		return customerLoyaltyDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CustomerLoyaltyDTO getCustomerLoyaltyDetailsByCustomerId(Long customnerId) {
		CustomerLoyalty customerLoyalty = customerLoyaltyRepository.findByCustomerId(customnerId);
		if(customerLoyalty==null){
			throw new BusinessException("Customer Loyalty not found :"+customnerId);
		}
		CustomerLoyaltyDTO customerLoyaltyDTO=customerLoyaltyMapper.customerLoyaltyToCustomerLoyaltyDTO(customerLoyalty);
		return customerLoyaltyDTO;
	}

}
