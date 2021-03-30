package com.medin.pharmacy.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.CategoryDTO;
import com.medin.pharmacy.service.ICategoryService;

@RestController
@RequestMapping(value="/category")
public class CategoryController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping(value="/add")
	public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO){
		return categoryService.addCategory(categoryDTO);
	}
	
	@PostMapping(value="/parentcategories")
	public List<CategoryDTO> getParentCategories(@RequestBody HashMap<String,String> requestPayload){
		return categoryService.getParentCategories(requestPayload);
	}
	
	@PostMapping(value="/{categoryId}")
	public List<CategoryDTO> getCategories(@PathVariable String categoryId){
		return categoryService.getChildCategoriesByParentCategoryId(categoryId);
	}
	

}
