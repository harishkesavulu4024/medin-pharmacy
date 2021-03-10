package com.medin.pharmacy.service.impl.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.medin.pharmacy.dto.StoreDTO;
import com.medin.pharmacy.dto.StoreScheduleDTO;
import com.medin.pharmacy.entities.Store;
import com.medin.pharmacy.entities.StoreSchedule;

@Mapper(componentModel = "spring", uses = {})
public interface StoreMapper {

	Store storeDTOTostore(StoreDTO storeDTO);

	StoreDTO storeToStoreDTO(Store store);
}
