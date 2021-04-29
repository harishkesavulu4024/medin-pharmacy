package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.ConfirmOrderDTO;
import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.service.ICustomerOrderService;
import com.medin.pharmacy.utils.CustomerValidationUtil;

@RestController
@RequestMapping(value = "/customerorder")
public class CustomerOrderController {

	@Autowired
	private ICustomerOrderService customerOrderService;

	@PostMapping(value = "/create")
	public CustomerOrderDTO createOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
		return customerOrderService.createOrder(customerOrderDTO);
	}

	@PutMapping(value = "/update")
	public CustomerOrderDTO updateOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
		return customerOrderService.updateOrder(customerOrderDTO);
	}

	@PostMapping(value = "/confirm")
	public CustomerOrderDTO confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO) {
		return customerOrderService.confirmOrder(confirmOrderDTO);
	}

	@GetMapping(value = "/{mobileNumber}")
	public List<CustomerOrderDTO> updateOrder(@PathVariable("mobileNumber") String mobileNumber) {
		CustomerValidationUtil.validateMobileNumberFormat(mobileNumber);
		return customerOrderService.getCustomerOrdersByMobileNumber(mobileNumber);
	}

	@GetMapping(value = "/{customerId}")
	public List<CustomerOrderDTO> updateOrder(@PathVariable("customerId") Long customerId) {
		return customerOrderService.getCustomerOrdersByCustomerId(customerId);
	}
}
