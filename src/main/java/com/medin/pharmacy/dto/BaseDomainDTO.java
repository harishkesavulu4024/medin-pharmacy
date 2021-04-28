package com.medin.pharmacy.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class BaseDomainDTO implements Serializable {
	
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	

	private Long id;
	
	private String createdBy;
	
	private String modifiedBy;
	
	private Date createdTime;
	
	private Date modifiedTime;

}