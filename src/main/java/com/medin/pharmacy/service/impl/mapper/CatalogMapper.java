package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CatalogDTO;
import com.medin.pharmacy.entities.Catalog;

@Mapper(componentModel = "spring", uses = {})
public interface CatalogMapper {
	
	Catalog catalogDTOTocatalog(CatalogDTO catalogDTO);
	
	CatalogDTO catalogTocatalogDTO(Catalog catalog);

}
