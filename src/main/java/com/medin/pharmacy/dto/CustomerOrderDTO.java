package com.medin.pharmacy.dto;

import java.util.List;

import com.medin.pharmacy.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerOrderDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long storeId;

	private Long customerId;

	private OrderStatus orderStatus;

	private String orderRefernceId;

	private double totalProductPrice;

	private double totalTaxPrice;

	private double totalProductCharges;

	private double totalAmount;

	private String status;
	 
	private List<CustomerOrderLineItemDTO> orderLineItems;
	
	private LoyaltyResponseDTO loyaltyData;
}
