package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.ProductUnit;
import com.medin.pharmacy.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "product")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product extends BaseEntity<String>{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="product_code",nullable=false)
	private String productCode;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name="image_url",nullable=false)
    private String imageUrl;
	
    private String description;
    
    @Column(name="product_name",nullable=false)
	private String productName;
    
    @Column(name="total_quantity")
    private Integer totalQuantity; 
    
    @Column(name="qunatity_per_pack")
    private Integer quantityPerPack;
    
    @Enumerated(EnumType.STRING)
    @Column(name="product_unit",nullable=false)
    private ProductUnit productUnit;
    
    @Column(name="selling_price")
    private Double sellingPrice;
    
    @Column(name="mrp_price")
    private Double mrpPrice;
    
    
}
