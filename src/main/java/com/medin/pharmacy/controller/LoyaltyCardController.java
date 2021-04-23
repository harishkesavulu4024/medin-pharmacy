package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.LoyaltyCardDTO;
import com.medin.pharmacy.service.ILoyaltyCardService;

@RestController
@RequestMapping(value = "/loyaltycard")
public class LoyaltyCardController {

	@Autowired
	private ILoyaltyCardService loyaltyCardService;

	@PostMapping(value = "/add")
	public LoyaltyCardDTO addLoyaltyCard(@RequestBody LoyaltyCardDTO loyaltyCardDTO) {
		return loyaltyCardService.addLoyaltyCard(loyaltyCardDTO);
	}

	@GetMapping(value = "/{loyaltyCardCode}")
	public LoyaltyCardDTO getLoyaltyCardByCode(@PathVariable("loyaltyCardCode") String loyaltyCardCode) {
		return loyaltyCardService.getLoyaltyCardByCode(loyaltyCardCode);
	}

	@GetMapping(value = "/list")
	public List<LoyaltyCardDTO> getListOfLoyaltyCards() {
		return loyaltyCardService.getListOfLoyaltyCards();
	}

}
