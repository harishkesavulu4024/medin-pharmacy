package com.medin.pharmacy.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreScheduleDTO extends BaseDomainDTO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dayOfWeek;

	private String status;

	private String openTime;

	private String closeTime;
	
	//private StoreDTO store;

}
