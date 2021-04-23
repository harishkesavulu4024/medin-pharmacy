package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>{

}
