package com.medin.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class CustomerLoyaltyDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long points;

	private CustomerDTO customer;

	private LoyaltyCardDTO loyaltyCard;

}
