package com.medin.pharmacy.dto;

import org.apache.commons.lang3.StringUtils;

import com.medin.pharmacy.enums.AddressType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressDTO extends BaseDomainDTO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private String addressLine1;

	private String addressLine2;

	private String street;

	private String city;

	private String district;

	private String state;

	private String country;

	private String zipCode;
	
	private AddressType addressType;
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(addressLine1 != null ? addressLine1 : "");
		stringBuilder.append(
				!StringUtils.isBlank(addressLine2) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(addressLine2 != null ? addressLine2 : "");
		stringBuilder.append(!StringUtils.isBlank(city) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(city != null ? city : "");
		stringBuilder
				.append(!StringUtils.isBlank(district) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(district != null ? district : "");
		stringBuilder.append(!StringUtils.isBlank(state) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(state != null ? state : "");
		stringBuilder.append(country != null ? country : "");
		stringBuilder
				.append(!StringUtils.isBlank(zipCode) && !StringUtils.isBlank(stringBuilder.toString()) ? "-" : "");
		stringBuilder.append(zipCode != null ? zipCode : "");
		return stringBuilder.toString();
	}

}
