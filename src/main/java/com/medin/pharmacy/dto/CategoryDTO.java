package com.medin.pharmacy.dto;

import java.util.List;

import com.medin.pharmacy.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryDTO extends BaseDomainDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String categoryCode;

	private Status status;

	private String categoryName;

	private String description;

	private Integer displayOrder;

	private CatalogDTO catalog;

	private StoreDTO store;

	private String parentCategoryCode;

	private String imageUrl;

	private List<CategoryDTO> categories;

	private List<ProductDTO> products;

}
