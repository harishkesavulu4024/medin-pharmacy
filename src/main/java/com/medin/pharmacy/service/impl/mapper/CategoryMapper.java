package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CategoryDTO;
import com.medin.pharmacy.entities.Category;

@Mapper(componentModel = "spring", uses = {CatalogMapper.class})
public interface CategoryMapper {
	
	Category categoryDTOToCategory(CategoryDTO categoryDTO);
	
	CategoryDTO categoryToCategoryDTO(Category category);

}
