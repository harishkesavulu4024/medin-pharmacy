package com.medin.pharmacy.dto;

import com.medin.pharmacy.enums.ProductUnit;
import com.medin.pharmacy.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productCode;

	private Status status;

	private String imageUrl;

	private String description;

	private String productName;

	private Integer totalQuantity;

	private Integer quantityPerPack;

	private ProductUnit productUnit;

	private Double sellingPrice;

	private Double mrpPrice;
	
	private String categoryCode;
	
	
}
