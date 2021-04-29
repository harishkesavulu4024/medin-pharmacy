package com.medin.pharmacy.service.impl.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.entities.CustomerOrder;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {
	
	CustomerOrder customerOrderDTOToCustomerOrder(CustomerOrderDTO customerOrderDTO);
	
	CustomerOrderDTO customerOrderToCustomerOrderDTO(CustomerOrder customerOrder);

	List<CustomerOrderDTO> listOfCustomerOrderToCustomerOrderDTO(List<CustomerOrder> customerOrders);
	
	List<CustomerOrder> listOfCustomerOrderDTOToCustomerOrder(List<CustomerOrderDTO> customerOrderDTOs);
}
