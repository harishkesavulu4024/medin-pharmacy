package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Catalog;

@Repository
public interface ICatalogRepository extends JpaRepository<Catalog, Long>{

	Catalog findByCatalogCode(String code);

}
