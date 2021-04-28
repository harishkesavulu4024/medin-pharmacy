package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_order_line_item")
public class CustomerOrderLineItem extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_order_id", nullable = false)
	private CustomerOrder customerOrder;

	@Column(name = "quantity")
	private Long quantity;
	
	@Column(name = "total_line_item_tax")
	private Double totalLineItemTax;
	
	@Column(name = "total_line_item_charges")
	private Double totalLineItemCharges;
	
	@Column(name = "total_line_item_price")
	private Double totalLineItemPrices;
	
	@Column(name = "total_line_item_amount")
	private Double totalLineItemAmount;
	
	@Column(name = "product_price")
	private Double productPrice;

	@Column(name = "product_id", nullable = false)
	private String productId;

}
