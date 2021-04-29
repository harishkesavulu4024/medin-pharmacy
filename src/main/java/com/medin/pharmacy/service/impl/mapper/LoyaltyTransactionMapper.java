package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.LoyaltyTransactionDTO;
import com.medin.pharmacy.entities.LoyaltyTransaction;

@Mapper(componentModel = "spring", uses = {CustomerLoyaltyMapper.class})
public interface LoyaltyTransactionMapper {
	
	LoyaltyTransaction loyaltyTransactionDTOToLoyaltyTransaction(LoyaltyTransactionDTO loyaltyTransactionDTO);
	
	LoyaltyTransactionDTO loyaltyTransactionToLoyaltyTransactionDTO(LoyaltyTransaction loyaltyTransaction);

}
