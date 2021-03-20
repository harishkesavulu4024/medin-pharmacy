package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
