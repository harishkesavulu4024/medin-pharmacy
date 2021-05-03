package com.medin.pharmacy.service;

import com.medin.pharmacy.dto.CustomerOrderDTO;
import com.medin.pharmacy.dto.LoyaltyResponseDTO;

public interface ILoyaltyTransactionService {

	LoyaltyResponseDTO earnableBurnablePoints(CustomerOrderDTO customerOrderDTO);

	LoyaltyResponseDTO processLoyaltyIssuance(CustomerOrderDTO dbCustomerOrderDTO);

	LoyaltyResponseDTO processLoyaltyRedemption(CustomerOrderDTO customerOrderDTO);

}
