package com.medin.pharmacy.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoyaltyLedgerDTO extends BaseDomainDTO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private Long points;

	private Long pointsRemaining;

	private Long balance;

	private String remarks;

	private Date expiryDate;
	
	private String details;

	private LoyaltyTransactionDTO loyaltyTransaction;


}
