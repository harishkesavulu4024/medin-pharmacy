package com.medin.pharmacy.dto;


import com.medin.pharmacy.enums.LoyaltyStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoyaltyTransactionDTO extends BaseDomainDTO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Long pointsEarned;
	
	private Long pointsRedeemed;
	
	private String loyaltyReferenceId;

	private String description;
	
	private String data;
	
	private CustomerLoyaltyDTO customerLoyalty;
	
	private String loyaltyType;
	
	private LoyaltyStatus loyaltyStatus;

}
