package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "category")
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="category_code",nullable=false)
	private String categoryCode;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name="category_name",nullable=false)
	private String categoryName;
	
	@Column(name="image_url",nullable=false)
	private String imageUrl;
	
	private String description;
	
	@Column(name="display_order", nullable = false)
	private Integer displayOrder;
	
	@ManyToOne
	@JoinColumn(name = "parent_category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "catalog_id")
	private Catalog catalog;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;
	
}
