package com.medin.pharmacy.dto;



import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class CurrencyDTO extends BaseDomainDTO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private String currencyCode;

	private String name;

	private Double conversionRatio;

}
