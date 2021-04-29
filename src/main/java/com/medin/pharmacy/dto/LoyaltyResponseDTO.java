package com.medin.pharmacy.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Builder
public class LoyaltyResponseDTO {
	
	private String loyaltyReferenceId;
	
	private double earnedPoints;
	
	private double burnedPoints;
	
	private double overallPoints;
	
	private Long customerId;
	

}
