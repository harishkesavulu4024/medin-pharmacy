package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.CurrencyDTO;
import com.medin.pharmacy.entities.Currency;

@Mapper(componentModel = "spring", uses = {})
public interface CurrencyyMapper {
	
	Currency currencyDTOToCurrency(CurrencyDTO currencyDTO);

	CurrencyDTO currencyToCurrencyDTO(Currency currency);
}
