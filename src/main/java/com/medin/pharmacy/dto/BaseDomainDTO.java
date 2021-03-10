package com.medin.pharmacy.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@SuppressWarnings(value = "serial")
public abstract class BaseDomainDTO implements Serializable {

	private Long id;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private Date createdTime;
	
	private Date modifiedTime;

	public BaseDomainDTO() {
		super();
	}

	public BaseDomainDTO(Long id) {
		super();
		this.id = id;
	}

}