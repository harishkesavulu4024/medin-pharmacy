package com.medin.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.LoyaltyLedger;

@Repository
public interface LoyaltyLedgerRepository extends JpaRepository<LoyaltyLedger, Long>{

}
