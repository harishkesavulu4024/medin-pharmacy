package com.medin.pharmacy.service;

import java.util.HashMap;
import java.util.List;

import com.medin.pharmacy.dto.CategoryDTO;

public interface ICategoryService {

	CategoryDTO addCategory(CategoryDTO categoryDTO);

	List<CategoryDTO> getParentCategories(HashMap<String, String> requestPayload);

	List<CategoryDTO> getCategoriesByCategoryId(String categoryId);

}
