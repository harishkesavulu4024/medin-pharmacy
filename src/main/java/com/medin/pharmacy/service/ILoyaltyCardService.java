package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.LoyaltyCardDTO;

public interface ILoyaltyCardService {

	LoyaltyCardDTO addLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO);

	LoyaltyCardDTO getLoyaltyCardByCode(String loyaltyCardCode);

	List<LoyaltyCardDTO> getListOfLoyaltyCards();

	LoyaltyCardDTO getDefaultLoyaltyCard();

}
