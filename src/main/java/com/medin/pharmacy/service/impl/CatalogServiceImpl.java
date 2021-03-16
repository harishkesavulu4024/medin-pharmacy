package com.medin.pharmacy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CatalogDTO;
import com.medin.pharmacy.entities.Catalog;
import com.medin.pharmacy.entities.Store;
import com.medin.pharmacy.enums.Status;
import com.medin.pharmacy.repository.ICatalogRepository;
import com.medin.pharmacy.service.ICatalogService;
import com.medin.pharmacy.service.impl.mapper.CatalogMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class CatalogServiceImpl implements ICatalogService {

	@Autowired
	private CatalogMapper catalogMapper;

	@Autowired
	private ICatalogRepository catalogRepository;
	
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CatalogServiceImpl.class);
	

	@Override
	@Transactional
	public CatalogDTO addCatalog(CatalogDTO catalogDTO) {
		String catalogCode = catalogDTO.getCatalogCode();
		String catalogName = catalogDTO.getName();
		Catalog existingCatalog = catalogRepository.findByCatalogCode(catalogCode);
		if (existingCatalog == null) {
			Catalog newCatalog = catalogRepository.save(catalogMapper.catalogDTOTocatalog(catalogDTO));
			return catalogMapper.catalogTocatalogDTO(newCatalog);
		} else {
			LOGGER.error("catalog  already exist");
			throw new BusinessException("catalog already exist");
		}
	}

	@Override
	@Transactional
	public CatalogDTO updateCatalog(CatalogDTO catalogDTO) {
		Long catalogId=catalogDTO.getId();
		String catalogCode = catalogDTO.getCatalogCode();
		String catalogName = catalogDTO.getName();
		Catalog existingCatalog = catalogRepository.findByCatalogCode(catalogCode);
		if (existingCatalog != null) {
			Catalog newCatalog = catalogRepository.save(catalogMapper.catalogDTOTocatalog(catalogDTO));
			return catalogMapper.catalogTocatalogDTO(newCatalog);
		} else {
			LOGGER.error("catalog  already exist");
			throw new BusinessException("catalog already exist");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatalogDTO> getListofCatalogs() {
		return catalogRepository.findAll().stream().filter(c -> c.getStatus().equals(Status.ACTIVE))
				.map(c -> catalogMapper.catalogTocatalogDTO(c)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public CatalogDTO getCatalogByCode(String code) {
		Catalog catalog = catalogRepository.findByCatalogCode(code);
		if (catalog != null) {
			CatalogDTO catalogDTO = catalogMapper.catalogTocatalogDTO(catalog);
			return catalogDTO;
		}

		throw new BusinessException("Catalog not found");
	}

}
