package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.LoyaltyCardDTO;
import com.medin.pharmacy.entities.LoyaltyCard;

@Mapper(componentModel = "spring", uses = {CurrencyyMapper.class})
public interface LoyaltyCardMapper {
 
	LoyaltyCard loyaltyCardDTOToLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO);
	
	LoyaltyCardDTO loyaltyCardToLoyaltyCardDTO(LoyaltyCard loyaltyCard);
	
}
