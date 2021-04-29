package com.medin.pharmacy.service.impl.mapper;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.LoyaltyLedgerDTO;
import com.medin.pharmacy.entities.LoyaltyLedger;

@Mapper(componentModel = "spring", uses = { LoyaltyTransactionMapper.class })
public interface LoyaltyLedgerMapper {
	
	LoyaltyLedger loyaltyLedgerDTOToLoyaltyLedger(LoyaltyLedgerDTO loyaltyLedgerDTO);
	
	LoyaltyLedgerDTO loyaltyLedgerToLoyaltyLedgerDTO(LoyaltyLedger loyaltyLedger);

}
