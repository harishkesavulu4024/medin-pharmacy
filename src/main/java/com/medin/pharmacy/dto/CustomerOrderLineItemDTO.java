package com.medin.pharmacy.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class CustomerOrderLineItemDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quantity;

	private double totalLineItemTax;

	private double totalLineItemCharges;

	private double totalLineItemPrices;

	private double totalLineItemAmount;

	private double productPrice;

	private String productId;
	

}
