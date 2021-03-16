package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.Status;

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
@Table(name = "product")
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
    
    
}
