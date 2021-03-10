package com.medin.pharmacy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medin.pharmacy.dto.StoreDTO;
import com.medin.pharmacy.dto.StoreScheduleDTO;
import com.medin.pharmacy.entities.Store;
import com.medin.pharmacy.entities.StoreSchedule;
import com.medin.pharmacy.enums.Status;
import com.medin.pharmacy.repository.StoreRepository;
import com.medin.pharmacy.repository.StoreScheduleRepository;
import com.medin.pharmacy.service.IStoreService;
import com.medin.pharmacy.service.impl.mapper.StoreMapper;
import com.medin.pharmacy.utils.BusinessException;

@Service
public class StoreServiceImpl implements IStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StoreServiceImpl.class);

	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreScheduleRepository storeScheduleRepository;
	@Autowired
	private StoreMapper storeMapper;

	@Override
	@Transactional
	public StoreDTO addStore(StoreDTO storeDTO) {
		String storeCode = storeDTO.getStoreCode();
		String storeName = storeDTO.getName();
		Store existingStore = storeRepository.findByStoreCode(storeCode);
		if (existingStore == null) {
			List<StoreScheduleDTO> storeSchedules=storeDTO.getStorcheules();
			Store newStore = storeRepository.save(storeMapper.storeDTOTostore(storeDTO));
			return storeMapper.storeToStoreDTO(newStore);
		} else {
			LOGGER.error("Store already exist");
			throw new BusinessException("Store already exist");
		}
	}

	@Override
	@Transactional
	public StoreDTO updateStore(StoreDTO storeDTO) {
		Long storeId=storeDTO.getId();
		String storeCode = storeDTO.getStoreCode();
		String storeName = storeDTO.getName();
		Store existingStore = storeRepository.findByStoreCode(storeCode);
		if (existingStore != null) {
			Store updateStore = storeRepository.save(storeMapper.storeDTOTostore(storeDTO));
			return storeMapper.storeToStoreDTO(updateStore);
		} else {
			LOGGER.error("Store not found");
			throw new BusinessException("Store not found");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public StoreDTO getStoreByCode(String code) {
		Store store = storeRepository.findByStoreCode(code);
		if (store != null) {
			StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);
			return storeDTO;
		} else {
			LOGGER.error("Store not found");
			throw new BusinessException("Store not found");
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreDTO> getListOfActiveStores() {
		List<Store> storeList = storeRepository.findAll();
		return storeList.stream().filter(store -> store.getStatus().equals(Status.ACTIVE))
				.map((store) -> storeMapper.storeToStoreDTO(store)).collect(Collectors.toList());
	}

	@Override
	public Boolean deleteStore(String idOrCode) {
		Store store = storeRepository.findByStoreCodeOrId(idOrCode);
		if (store != null) {
			store.setStatus(Status.INACTIVE);
			storeRepository.delete(store);
			return true;
		} else {
			LOGGER.error("Store not found");
			throw new BusinessException("Store not found");
		}
	}

}
