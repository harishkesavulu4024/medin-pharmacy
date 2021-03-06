package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "customer")
@EntityListeners(AuditingEntityListener.class)
public class Customer extends BaseEntity<String> {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="mobile_number",unique=true,nullable = false)
	private String mobileNumber;
	
	private String name;
	
	private String email;
	
	@Column(nullable = false, columnDefinition = "varchar(40) default 'ACTIVE'")
	private String status;
	
	

}
