package com.medin.pharmacy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medin.pharmacy.repository.ProductCatalogRepository;
import com.medin.pharmacy.repository.ProductRepository;
import com.medin.pharmacy.service.IProductService;
import com.medin.pharmacy.service.impl.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductCatalogRepository productCatalogRepository;

}
