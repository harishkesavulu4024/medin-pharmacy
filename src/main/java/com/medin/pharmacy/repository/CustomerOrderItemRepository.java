package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.CustomerOrderLineItem;

@Repository
public interface CustomerOrderItemRepository extends JpaRepository<CustomerOrderLineItem, Long> {

	void deleteByCustomerOrderId(Long customerOrderId);

}
