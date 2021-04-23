package com.medin.pharmacy.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoyaltyCardDTO extends BaseDomainDTO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String loyaltyCardCode;

	private String description;

	private String loyaltyCardName;

	private CurrencyDTO currency;

}
