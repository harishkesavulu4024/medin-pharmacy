package com.medin.pharmacy.dto;

import java.util.List;

import com.medin.pharmacy.entities.Address;
import com.medin.pharmacy.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreDTO extends BaseDomainDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String imageUrl;

	private String storeCode;

	private String name;
	
	private Status status;
	
	private String storeType;
	
	private String shortName;

	private String latitude;

	private String longitude;

	private String rating;
		
	private String description;

	private List<StoreScheduleDTO> storcheules;
	
	private AddressDTO address;

}
