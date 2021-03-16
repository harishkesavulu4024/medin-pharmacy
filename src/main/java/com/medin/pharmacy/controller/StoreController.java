package com.medin.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medin.pharmacy.dto.StoreDTO;
import com.medin.pharmacy.service.IStoreService;

@RestController
@RequestMapping(value = "/store")
public class StoreController {

	@Autowired
	private IStoreService storeService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public StoreDTO addStore(@RequestBody StoreDTO storeDTO) {
		return storeService.addStore(storeDTO);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public StoreDTO updateStore(@RequestBody StoreDTO storeDTO) {
		return storeService.updateStore(storeDTO);
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public StoreDTO getStoreByCode(@PathVariable String code) {
		return storeService.getStoreByCode(code);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<StoreDTO> getListOfActiveStores() {
		return storeService.getListOfActiveStores();
	}
	
	@RequestMapping(value = "/delete/{code}", method = RequestMethod.DELETE)
	public Boolean deleteStore(@PathVariable String code) {
		return storeService.deleteStore(code);
	}

}
