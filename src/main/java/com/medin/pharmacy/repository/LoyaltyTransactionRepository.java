package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.LoyaltyTransaction;

@Repository
public interface LoyaltyTransactionRepository extends JpaRepository<LoyaltyTransaction, Long>{

}
