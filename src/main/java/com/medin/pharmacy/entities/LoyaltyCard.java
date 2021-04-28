package com.medin.pharmacy.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "loyalty_card")
@EntityListeners(AuditingEntityListener.class)
public class LoyaltyCard extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "loyalty_card_code", nullable = false,unique=true)
	private String loyaltyCardCode;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "loyalty_card_name", nullable = false)
	private String loyaltyCardName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "currency_id")
	private Currency currency;

}
