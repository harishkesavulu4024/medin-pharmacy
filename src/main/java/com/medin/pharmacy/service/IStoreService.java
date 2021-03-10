package com.medin.pharmacy.service;

import java.util.List;

import com.medin.pharmacy.dto.StoreDTO;

public interface IStoreService {

	StoreDTO addStore(StoreDTO storeDTO);

	StoreDTO updateStore(StoreDTO storeDTO);

	StoreDTO getStoreByCode(String code);

	List<StoreDTO> getListOfActiveStores();

	Boolean deleteStore(String idOrCode);

}
