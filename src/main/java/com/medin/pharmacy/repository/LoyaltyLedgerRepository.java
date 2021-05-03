package com.medin.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.medin.pharmacy.entities.LoyaltyLedger;

@Repository
public interface LoyaltyLedgerRepository extends JpaRepository<LoyaltyLedger, Long>{

	@Query("select loyaltyLedger from LoyaltyLedger loyaltyLedger where loyaltyLedger.customerId=:customerId and loyaltyLedger.pointsRemaining > 0 and date(loyaltyLedger.expiryDate) >=curdate() order by loyaltyLedger.expiryDate")
	List<LoyaltyLedger> findEarnLedgerTransactionByCustomerId(@Param("customerId") Long customerId);

}
