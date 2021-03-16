package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.CatalogDTO;

public interface ICatalogService {

	CatalogDTO addCatalog(CatalogDTO catalogDTO);

	CatalogDTO updateCatalog(CatalogDTO catalogDTO);

	List<CatalogDTO> getListofCatalogs();

	CatalogDTO getCatalogByCode(String code);

}
