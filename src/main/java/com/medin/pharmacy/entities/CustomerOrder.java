package com.medin.pharmacy.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.OrderStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer_order", indexes = { @Index(columnList = "customer_id,order_refernce_id") })
public class CustomerOrder extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "store_id", nullable = false)
	private Long storeId;

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status", nullable = false)
	private OrderStatus orderStatus;

	@Column(name = "order_refernce_id", nullable = false)
	private String orderRefernceId;

	@Column(name = "total_product_price")
	private Double totalProductPrice;

	@Column(name = "total_tax_price")
	private Double totalTaxPrice;

	@Column(name = "total_product_charges")
	private Double totalProductCharges;

	@Column(name = "total_amount")
	private Double totalAmount;

	@Column(nullable = false, columnDefinition = "varchar(40) default 'ACTIVE'")
	private String status;
	
	@Setter(AccessLevel.NONE)
	@OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerOrderLineItem> orderLineItems;
	
	public void setOrderLineItems(List<CustomerOrderLineItem> orderLineItems) {
		this.orderLineItems = orderLineItems;
		if (this.orderLineItems != null) {
			for (CustomerOrderLineItem orderLineItem : this.orderLineItems) {
				orderLineItem.setCustomerOrder(this);
			}
		}
	}
	
	
}
