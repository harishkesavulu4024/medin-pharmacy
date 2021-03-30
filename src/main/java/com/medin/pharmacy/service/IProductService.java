package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.ProductCategoryDTO;
import com.medin.pharmacy.dto.ProductDTO;

public interface IProductService {

	ProductDTO addProduct(ProductDTO productDTO);

	List<ProductCategoryDTO> getProductDetailsByCategoryId(String categoryId);

}
