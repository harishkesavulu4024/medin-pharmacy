package com.medin.pharmacy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.CurrencyDTO;
import com.medin.pharmacy.dto.LoyaltyCardDTO;
import com.medin.pharmacy.entities.LoyaltyCard;
import com.medin.pharmacy.repository.LoyaltyCardRepository;
import com.medin.pharmacy.service.ILoyaltyCardService;
import com.medin.pharmacy.service.impl.mapper.LoyaltyCardMapper;

@Service
public class LoyaltyCardServiceImpl implements ILoyaltyCardService {

	@Autowired
	private LoyaltyCardMapper loyaltyCardMapper;

	@Autowired
	private LoyaltyCardRepository loyaltyCardRepository;

	@Override
	@Transactional
	public LoyaltyCardDTO addLoyaltyCard(LoyaltyCardDTO loyaltyCardDTO) {
		//CurrencyDTO currencyDTO = loyaltyCardDTO.getCurrency();
		LoyaltyCard dbLoyaltyCard = loyaltyCardRepository
				.save(loyaltyCardMapper.loyaltyCardDTOToLoyaltyCard(loyaltyCardDTO));
		LoyaltyCardDTO dbLoyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(dbLoyaltyCard);
		return dbLoyaltyCardDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoyaltyCardDTO getLoyaltyCardByCode(String loyaltyCardCode) {
		LoyaltyCard dbLoyaltyCard = loyaltyCardRepository.findByLoyaltyCardCode(loyaltyCardCode);
		LoyaltyCardDTO loyaltyCardDTO = loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(dbLoyaltyCard);
		return loyaltyCardDTO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoyaltyCardDTO> getListOfLoyaltyCards() {
		List<LoyaltyCardDTO> loyaltyCardList = loyaltyCardRepository.findAll().stream()
				.map(lc -> loyaltyCardMapper.loyaltyCardToLoyaltyCardDTO(lc)).collect(Collectors.toList());
		return loyaltyCardList;
	}
	
	@Override
	@Transactional(readOnly = true)
	public LoyaltyCardDTO getDefaultLoyaltyCard() {
		LoyaltyCardDTO loyaltyCardDTO=null;
		List<LoyaltyCardDTO> loyaltyCardList = getListOfLoyaltyCards();
		if(loyaltyCardList!=null && !loyaltyCardList.isEmpty()){
			loyaltyCardDTO=loyaltyCardList.get(0);
		}
		return loyaltyCardDTO;
	}
	
	

}
