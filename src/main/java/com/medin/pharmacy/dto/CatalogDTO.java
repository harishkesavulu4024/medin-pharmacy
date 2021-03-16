package com.medin.pharmacy.dto;

import com.medin.pharmacy.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CatalogDTO extends BaseDomainDTO {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Status status;
	
	private String description;
	
	private String catalogCode;
}
