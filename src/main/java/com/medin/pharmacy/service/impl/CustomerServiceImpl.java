package com.medin.pharmacy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CustomerDTO;
import com.medin.pharmacy.dto.CustomerLoyaltyDTO;
import com.medin.pharmacy.entities.Customer;
import com.medin.pharmacy.entities.CustomerLoyalty;
import com.medin.pharmacy.repository.CustomerRepository;
import com.medin.pharmacy.repository.QueueRepository;
import com.medin.pharmacy.service.ICustomerLoyaltyService;
import com.medin.pharmacy.service.ICustomerService;
import com.medin.pharmacy.service.ILoyaltyCardService;
import com.medin.pharmacy.service.impl.mapper.CustomerMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private QueueRepository queueRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ICustomerLoyaltyService customerLoyaltyService;

	@Autowired
	private ILoyaltyCardService loyaltyCardService;

	@Value("${loyalty.enabled:true}")
	private boolean isLoyaltyEnabled;

	@Override
	@Transactional
	public CustomerDTO addCustomerDetails(CustomerDTO customerDTO) {
		String mobileNumber = customerDTO.getMobileNumber();
		Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber);
		if (existingCustomer != null) {
			throw new BusinessException("Customer with phone -" + mobileNumber + " already exits");
		}
		Customer dbCustomer = customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO));
		CustomerDTO dbCustomerDTO = customerMapper.customerToCustomerDTO(dbCustomer);
		// checking customer loyalty enabled or not
		if (isLoyaltyEnabled) {
			// setting 0 points by default for all customers
			CustomerLoyaltyDTO customerLoyaltyDTO = CustomerLoyaltyDTO.builder().customer(dbCustomerDTO).points(0l)
					.build();
			CustomerLoyaltyDTO dbcustomerLoyaltyDTO = customerLoyaltyService.createCustomerLoyalty(customerLoyaltyDTO);
		}
		return dbCustomerDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerDTO getCustomerDetailsByPhoneNumber(String mobileNumber) {
		Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber);
		if (existingCustomer == null) {
			throw new BusinessException("Customer with phone -" + mobileNumber + " not exits");
		}
		CustomerDTO existingCustomerDTO = customerMapper.customerToCustomerDTO(existingCustomer);
		return existingCustomerDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerDTO> getActiveCustomerDetails() {
		List<CustomerDTO> activeCustomerList = customerRepository.findAll().stream()
				.filter(c -> c.getStatus().equals("ACTIVE")).map(c -> customerMapper.customerToCustomerDTO(c))
				.collect(Collectors.toList());
		return activeCustomerList;
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerDTO getCustomerById(Long customerId) {
		CustomerDTO existingCustomerDTO = null;
		Optional<Customer> existingCustomerData = customerRepository.findById(customerId);
		if (existingCustomerData.isPresent()) {
			existingCustomerDTO = customerMapper.customerToCustomerDTO(existingCustomerData.get());
		}
		return existingCustomerDTO;
	}

}
