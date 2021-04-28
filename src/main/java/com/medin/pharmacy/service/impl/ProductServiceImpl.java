package com.medin.pharmacy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.ProductCategoryDTO;
import com.medin.pharmacy.dto.ProductDTO;
import com.medin.pharmacy.entities.Category;
import com.medin.pharmacy.entities.Product;
import com.medin.pharmacy.entities.ProductCategory;
import com.medin.pharmacy.repository.CategoryRepository;
import com.medin.pharmacy.repository.ProductCategoryRepository;
import com.medin.pharmacy.repository.ProductRepository;
import com.medin.pharmacy.service.IProductService;
import com.medin.pharmacy.service.impl.mapper.ProductCategoryMapper;
import com.medin.pharmacy.service.impl.mapper.ProductMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductCategoryRepository productCategorygRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Override
	@Transactional
	public ProductDTO addProduct(ProductDTO productDTO) {
		String categoryCode = productDTO.getCategoryCode();
		String productCode = productDTO.getProductCode();
		String productName = productDTO.getProductName();
		Category category = categoryRepository.findByCategoryCode(categoryCode);
		if (category == null) {
			throw new BusinessException("Category not found with code _" + categoryCode);
		}
		ProductCategory dbProductCategory = productCategorygRepository.findByProductCodeAndCategoryCode(productCode,
				categoryCode);
		if (dbProductCategory != null) {
			throw new BusinessException("Product already exits in the same category");
		}
		// add product
		Product product = productRepository.save(productMapper.productDTOTOProduct(productDTO));
		// adding product category
		ProductCategory productCategory = productCategorygRepository
				.save(ProductCategory.builder().category(category).product(product).build());
		return productMapper.productToProductDTO(product);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductCategoryDTO> getProductDetailsByCategoryId(String categoryId) {
		// Get the root category
		Optional<Category> categoryData = categoryRepository.findById(Long.valueOf(categoryId));
		if (!categoryData.isPresent())
			throw new BusinessException("Category not found with id _" + categoryId);
		Category category = categoryData.get();
		List<Category> childCategories = categoryRepository
				.findChildCategoriesByParentCategoryId(Long.valueOf(categoryId));
		if (childCategories != null && !childCategories.isEmpty()) {
			List<ProductCategory> productCategories = productCategorygRepository
					.findProductcategoriesByParentcategoryId(Long.valueOf(categoryId));
			if (productCategories != null && !productCategories.isEmpty()) {
				List<ProductCategoryDTO> productCategoriesDTO = productCategories.stream()
						.map(pc -> productCategoryMapper.productCategoryToProductCategoryDTO(pc))
						.collect(Collectors.toList());
				return productCategoriesDTO;
			}
		} else {
			throw new BusinessException("No child category Category found");
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDTO getProductById(String productId) {
		ProductDTO productDTO = null;
		Optional<Product> dbProduct = productRepository.findById(Long.valueOf(productId));
		if (dbProduct.isPresent()) {
			productDTO = productMapper.productToProductDTO(dbProduct.get());
		}
		return productDTO;
	}

}
