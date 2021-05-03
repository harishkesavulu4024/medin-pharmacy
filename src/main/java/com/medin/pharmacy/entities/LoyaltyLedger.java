package com.medin.pharmacy.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.LedgerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "loyalty_ledger")
@EntityListeners(AuditingEntityListener.class)
public class LoyaltyLedger extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private Long points;

	@Column(name = "points_remaining")
	private Long pointsRemaining;

	private Long balance;

	private String remarks;
	
	@Enumerated(EnumType.STRING)
	private LedgerType type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiry_date")
	private Date expiryDate;
	
	@Column(columnDefinition="TEXT")
	private String details;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loyalty_transaction_id", nullable = false)
	private LoyaltyTransaction loyaltyTransaction;
	
	@Column(name = "customer_id", nullable = false)
	private Long customerId;

}
