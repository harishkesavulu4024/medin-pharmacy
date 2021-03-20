package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.ProductCategory;

@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCategory, Long> {

}
