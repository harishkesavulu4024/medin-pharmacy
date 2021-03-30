package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.ProductCategoryDTO;
import com.medin.pharmacy.dto.ProductDTO;
import com.medin.pharmacy.service.IProductService;

@RestController
@RequestMapping(value="/product")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	@PostMapping(value="/add")
	public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
		return productService.addProduct(productDTO);
	}
	
	@GetMapping(value="/categories/{categoryId}")
	public List<ProductCategoryDTO> getProductDetailsByCategoryId(@PathVariable String categoryId){
		return productService.getProductDetailsByCategoryId(categoryId);
	}

}
