package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.ConfirmOrderDTO;
import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.entities.CustomerOrder;

public interface ICustomerOrderService {

	CustomerOrderDTO createOrder(CustomerOrderDTO customerOrderDTO);

	CustomerOrderDTO updateOrder(CustomerOrderDTO customerOrderDTO);

	Boolean checkOrderExpiry(CustomerOrder customerOrder);

	List<CustomerOrderDTO> getCustomerOrdersByMobileNumber(String mobileNumber);

	List<CustomerOrderDTO> getCustomerOrdersByCustomerId(Long customerId);

	CustomerOrderDTO confirmOrder(ConfirmOrderDTO confirmOrderDTO);

}
