package com.medin.pharmacy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CatalogDTO;
import com.medin.pharmacy.dto.CategoryDTO;
import com.medin.pharmacy.dto.StoreDTO;
import com.medin.pharmacy.entities.Catalog;
import com.medin.pharmacy.entities.Category;
import com.medin.pharmacy.entities.Store;
import com.medin.pharmacy.repository.CategoryRepository;
import com.medin.pharmacy.repository.ICatalogRepository;
import com.medin.pharmacy.repository.StoreRepository;
import com.medin.pharmacy.service.ICategoryService;
import com.medin.pharmacy.service.impl.mapper.CategoryMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class CategoryServieImpl implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private ICatalogRepository catalogRepository;

	@Override
	@Transactional
	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		String parentCode = categoryDTO.getParentCategoryCode();
		String categoryCode = categoryDTO.getCategoryCode();
		StoreDTO storeDTO = categoryDTO.getStore();
		CatalogDTO catalogDTO=categoryDTO.getCatalog();
		// Validate input
	    validateStoreAndCatalogInput(storeDTO.getId(),catalogDTO.getId());
		Category existingCategory = categoryRepository.findByCategoryCodeAndStoreCode(categoryCode,
				storeDTO.getStoreCode());
		if (existingCategory != null) {
			throw new BusinessException("category already exits with code " + categoryCode);
		}
		Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
		Category parentCategory = categoryRepository.findByCategoryCodeAndStoreCode(parentCode,
				storeDTO.getStoreCode());
		if (parentCategory != null) {
			category.setCategory(parentCategory);
		}
		CategoryDTO newcategoryDTO = categoryMapper.categoryToCategoryDTO(categoryRepository.save(category));
		return newcategoryDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryDTO> getParentCategories(HashMap<String, String> requestPayload) {
		Long storeId = Long.valueOf(requestPayload.get("storeId"));
		Long catalogId = Long.valueOf(requestPayload.get("catalogId"));
		// Validate input
		validateStoreAndCatalogInput(storeId,catalogId);
		List<Category> parentcategoryList = categoryRepository.findParentCategoriesByStoreIdAndCatalogId(storeId,
				catalogId);
		return parentcategoryList.stream().map(c -> categoryMapper.categoryToCategoryDTO(c))
				.collect(Collectors.toList());
	}
	
	private void validateStoreAndCatalogInput(Long storeId,Long catalogId){
		Optional<Store> store=storeRepository.findById(storeId);
		if(!store.isPresent()){
			throw new BusinessException("Store not found with id "+storeId);
		}
		Optional<Catalog> catalog=catalogRepository.findById(catalogId);
		if(!catalog.isPresent()){
			throw new BusinessException("Catalog not found with id "+catalogId);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoryDTO> getCategoriesByCategoryId(String categoryId) {
		return null;
	}

}
