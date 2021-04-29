package com.medin.pharmacy.dto;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class CustomerOrderRequestDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String storeCode;
	
	private String orderRefernceId;
	
	private String status;
	
	private Long customerId;
	
	private String mobileNumber;
	 
	

}
