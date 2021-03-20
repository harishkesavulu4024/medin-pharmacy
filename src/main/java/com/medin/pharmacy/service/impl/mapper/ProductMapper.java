package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.ProductDTO;
import com.medin.pharmacy.entities.Product;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

	Product productDTOTOProduct(ProductDTO productDTO);

	ProductDTO productToProductDTO(Product product);

}
