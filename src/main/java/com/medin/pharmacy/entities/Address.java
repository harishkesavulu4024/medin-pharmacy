package com.medin.pharmacy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.medin.pharmacy.enums.AddressType;

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
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
public class Address extends BaseEntity<String> {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Column(name = "address_line1", length = 500)
	private String addressLine1;

	@Column(name = "address_line2", length = 500)
	private String addressLine2;
	
	@Column(name = "street", nullable = false, length = 500)
	private String street;

	@Column(name = "city", length = 20)
	private String city;
	
	@Column(name = "district", length = 100)
	private String district;

	@Column(name = "state", length = 20)
	private String state;
	
	@Column(name = "country", length = 20)
	private String country;

	@Column(name = "zip_code")
	private String zipCode;
	
	@Column(name = "address_type", length = 20)
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(addressLine1 != null ? addressLine1 : "");
		stringBuilder.append(
				!StringUtils.isBlank(addressLine2) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(addressLine2 != null ? addressLine2 : "");
		stringBuilder.append(!StringUtils.isBlank(city) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(city != null ? city : "");
		stringBuilder
				.append(!StringUtils.isBlank(district) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(district != null ? district : "");
		stringBuilder.append(!StringUtils.isBlank(state) && !StringUtils.isBlank(stringBuilder.toString()) ? "," : "");
		stringBuilder.append(state != null ? state : "");
		stringBuilder.append(country != null ? country : "");
		stringBuilder
				.append(!StringUtils.isBlank(zipCode) && !StringUtils.isBlank(stringBuilder.toString()) ? "-" : "");
		stringBuilder.append(zipCode != null ? zipCode : "");
		return stringBuilder.toString();
	}


}
