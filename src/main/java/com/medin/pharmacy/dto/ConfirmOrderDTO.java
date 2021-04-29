package com.medin.pharmacy.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ConfirmOrderDTO {

	private Long orderId;
	
	private Long customerId;
	

}