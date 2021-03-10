package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

	Store findByStoreCode(String code);
	
    @Query("select store from Store store where store.id=:idOrCode or store.storeCode=:idOrCode")
	Store findByStoreCodeOrId(@Param("idOrCode") String idOrCode);

}
