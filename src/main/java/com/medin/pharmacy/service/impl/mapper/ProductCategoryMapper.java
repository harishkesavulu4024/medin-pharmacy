package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.ProductCategoryDTO;
import com.medin.pharmacy.entities.ProductCategory;

@Mapper(componentModel = "spring", uses = {})
public interface ProductCategoryMapper {
	
	ProductCategoryDTO productCategoryToProductCategoryDTO(ProductCategory productCategory);
	
	ProductCategory productCategoryDTOToProductCategory(ProductCategoryDTO productCategoryDTO);

}
