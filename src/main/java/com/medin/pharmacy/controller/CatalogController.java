package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.CatalogDTO;
import com.medin.pharmacy.service.ICatalogService;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {

	@Autowired
	private ICatalogService catalogService;

	@PostMapping(value = "/add")
	public CatalogDTO addCatalog(@RequestBody CatalogDTO catalogDTO) {
		return catalogService.addCatalog(catalogDTO);
	}

	@PutMapping(value = "/update")
	public CatalogDTO updateCatalog(@RequestBody CatalogDTO catalogDTO) {
		return catalogService.updateCatalog(catalogDTO);
	}

	@GetMapping(value = "/list")
	public List<CatalogDTO> getListofCatalogs() {
		return catalogService.getListofCatalogs();
	}

	@GetMapping(value = "/{code}")
	public CatalogDTO getCatalogByCode(@PathVariable String code) {
		return catalogService.getCatalogByCode(code);
	}

}
