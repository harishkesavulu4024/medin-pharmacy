package com.medin.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.service.IProductService;

@RestController
public class ProductController {

	@Autowired
	private IProductService productService;

}
