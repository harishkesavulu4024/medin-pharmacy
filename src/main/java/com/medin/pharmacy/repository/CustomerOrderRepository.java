package com.medin.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.entities.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {


	CustomerOrder findByIdAndCustomerId(Long customerOrderId, Long customerId);

	List<CustomerOrder> findByCustomerId(Long customerId);

}
