package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.LoyaltyStatus;

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
@Table(name = "loyalty_transaction")
@EntityListeners(AuditingEntityListener.class)
public class LoyaltyTransaction extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	@Column(name="points_earned")
	private Long pointsEarned;
	
	@Column(name="points_redeemed")
	private Long pointsRedeemed;
	
	@Column(name="loyalty_reference_id",nullable=false)
	private String loyaltyReferenceId;

	private String description;
	
	@Column(columnDefinition="TEXT")
	private String data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_loyalty_id",nullable=false)
	private CustomerLoyalty customerLoyalty;
	
	@Column(name="loyalty_type",nullable=false)
	private String loyaltyType;
	
	@Enumerated(EnumType.STRING)
	@Column(name="loyalty_status",nullable=false)
	private LoyaltyStatus loyaltyStatus;
}
