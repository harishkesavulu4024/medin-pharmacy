package com.medin.pharmacy.dto;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings(value = "serial")
public abstract class BaseDomainDTO implements Serializable {

	private Long id;

	public BaseDomainDTO() {
		super();
	}

	public BaseDomainDTO(Long id) {
		super();
		this.id = id;
	}

}